package com.stellago.stellago;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.database.DatabaseHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parser.JSONParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DepositPage extends AppCompatActivity implements OnMapReadyCallback,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleMap.OnMarkerClickListener,
        GoogleApiClient.OnConnectionFailedListener{
    private DatabaseHelper helper;
    private List<Branch> branchList;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_page);

        helper = new DatabaseHelper(this);
        branchList = helper.selectAllBranch();
        final Spinner dropdown = (Spinner)findViewById(R.id.monthForPaymentSpinner);
        List<Branch> branchList = helper.selectAllBranch();
        ArrayAdapter<Branch> adapter = new ArrayAdapter<Branch>(this, R.layout.spinner_item, branchList);
        dropdown.setAdapter(adapter);

        final CheckBox nearest = (CheckBox) findViewById(R.id.nearbyCheckbox);
        final CheckBox safest = (CheckBox) findViewById(R.id.safestCheckbox);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        //Initialize Google Play Services
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            mMap = googleMap;
//            if (ContextCompat.checkSelfPermission(this,
//                    android.Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//                buildGoogleApiClient();
//                mMap.setMyLocationEnabled(true);
//            }
//        }
//        else {
//            buildGoogleApiClient();
//            mMap.setMyLocationEnabled(true);
//        }
        GPSTracker gps = new GPSTracker(this);
        currentLocation = new LatLng(gps.getLatitude(),gps.getLongitude());

        Button submitButton = (Button) findViewById(R.id.depositSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toConfirmationPage = new Intent(getApplicationContext(), ConfirmationPage.class);
                toConfirmationPage.putExtra("branchDetails", (Branch)dropdown.getSelectedItem());
                startActivity(toConfirmationPage);
            }
        });

        nearest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                boolean isNearestChecked = nearest.isChecked();
                boolean isSafestChecked = safest.isChecked();
                if (isNearestChecked && isSafestChecked) {

                } else if (isNearestChecked) {
                    List<Branch> sortedByDistance = sortByDistance();
                    ArrayAdapter<Branch> adapterSorted = new ArrayAdapter<Branch>(getApplicationContext(), R.layout.spinner_item, sortedByDistance);
                    dropdown.setAdapter(adapterSorted);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private List<Branch> sortByDistance() {
        List<Branch> sortedByDistance = new ArrayList<Branch>();
        double currentLat = currentLocation.latitude;
        double currentLong = currentLocation.longitude;
        double R = 6371; // radius of earth in km
        Map<Integer, Double> withDistances = new HashMap<>();
        Log.d("mylat " + currentLocation.latitude,"");
        Log.d("mylong " + currentLocation.longitude,"");
        int closest = -1;
        for(int i = 0;i < branchList.size(); i++ ) {
            double mlat = branchList.get(i).getBranchLatitude();
            double mlng = branchList.get(i).getBranchLongitude();
//            double dLat = Math.toRadians(mlat - currentLat);
//            double dLong = Math.toRadians(mlng - currentLong);
//            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                    Math.cos(Math.toRadians(currentLat)) * Math.cos(Math.toRadians(currentLat)) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
//            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//            double d = R * c;
            Location loc1 = new Location("");
            loc1.setLatitude(currentLat);
            loc1.setLongitude(currentLong);

            Location loc2 = new Location("");
            loc2.setLatitude(mlat);
            loc2.setLongitude(mlng);
            double d = loc1.distanceTo(loc2);
            withDistances.put(branchList.get(i).getBranchID(), d);
            Log.d("branch = " + branchList.get(i).getBranchName(), withDistances.get(branchList.get(i).getBranchID()).toString());
        }

        for (int j = 0; j < withDistances.size()-1; j++) {
        /* find the min element in the unsorted a[j .. n-1] */
        /* assume the min is the first element */
            int iMin = j;
        /* test against elements after j to find the smallest */
            for (int k = j+1; k < withDistances.size(); k++) {
            /* if this element is less, then it is the new minimum */
                if (withDistances.get(branchList.get(k).getBranchID()) < withDistances.get(branchList.get(iMin).getBranchID())) {
            /* found new minimum; remember its index */
                    iMin = k;
                }
            }
            if(iMin != j) {
                Collections.swap(branchList,j,iMin);
            }
        }

        for (int l = 0; l < branchList.size(); l++) {
            Log.d("branch = " + branchList.get(l).getBranchName(), withDistances.get(branchList.get(l).getBranchID()).toString());
        }
        return  branchList;
    }


}
