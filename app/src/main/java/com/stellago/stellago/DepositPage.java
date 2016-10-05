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

public class DepositPage extends AppCompatActivity {
    private DatabaseHelper helper;
    private List<Branch> branchList;
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
                    List<Branch> sortedByDistanceAndRate = sortByDistanceAndRate();
                    ArrayAdapter<Branch> adapterSorted = new ArrayAdapter<Branch>(getApplicationContext(), R.layout.spinner_item, sortedByDistanceAndRate);
                    dropdown.setAdapter(adapterSorted);
                } else if (isNearestChecked) {
                    List<Branch> sortedByDistance = sortByDistance();
                    ArrayAdapter<Branch> adapterSorted = new ArrayAdapter<Branch>(getApplicationContext(), R.layout.spinner_item, sortedByDistance);
                    dropdown.setAdapter(adapterSorted);
                } else if (isSafestChecked) {
                    List<Branch> sortedByRate = sortByRate();
                    ArrayAdapter<Branch> adapterSorted = new ArrayAdapter<Branch>(getApplicationContext(), R.layout.spinner_item, sortedByRate);
                    dropdown.setAdapter(adapterSorted);
                }
            }
        });

        safest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                boolean isNearestChecked = nearest.isChecked();
                boolean isSafestChecked = safest.isChecked();
                if (isNearestChecked && isSafestChecked) {
                    List<Branch> sortedByDistanceAndRate = sortByDistanceAndRate();
                    ArrayAdapter<Branch> adapterSorted = new ArrayAdapter<Branch>(getApplicationContext(), R.layout.spinner_item, sortedByDistanceAndRate);
                    dropdown.setAdapter(adapterSorted);
                } else if (isNearestChecked) {
                    List<Branch> sortedByDistance = sortByDistance();
                    ArrayAdapter<Branch> adapterSorted = new ArrayAdapter<Branch>(getApplicationContext(), R.layout.spinner_item, sortedByDistance);
                    dropdown.setAdapter(adapterSorted);
                } else if (isSafestChecked) {
                    List<Branch> sortedByRate = sortByRate();
                    ArrayAdapter<Branch> adapterSorted = new ArrayAdapter<Branch>(getApplicationContext(), R.layout.spinner_item, sortedByRate);
                    dropdown.setAdapter(adapterSorted);
                }
            }
        });
    }

    private List<Branch> sortByDistance() {
        List<Branch> sortedByDistance = branchList;
        Map<Integer, Double> withDistances = new HashMap<>();
        for(int i = 0;i < branchList.size(); i++ ) {
            double mlat = branchList.get(i).getBranchLatitude();
            double mlng = branchList.get(i).getBranchLongitude();
            Location loc1 = new Location("");
            loc1.setLatitude(currentLocation.latitude);
            loc1.setLongitude(currentLocation.longitude);

            Location loc2 = new Location("");
            loc2.setLatitude(mlat);
            loc2.setLongitude(mlng);
            double d = loc1.distanceTo(loc2);
            withDistances.put(branchList.get(i).getBranchID(), d);
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
                Collections.swap(sortedByDistance,j,iMin);
            }
        }
        return sortedByDistance;
    }

    private List<Branch> sortByRate() {
        List<Branch> sortedByRate = branchList;
        for (int j = 0; j < sortedByRate.size()-1; j++) {
        /* find the min element in the unsorted a[j .. n-1] */
        /* assume the min is the first element */
            int iMax = j;
        /* test against elements after j to find the smallest */
            for (int k = j+1; k < sortedByRate.size(); k++) {
            /* if this element is less, then it is the new minimum */
                if (branchList.get(iMax).getBranchRateAverage() < branchList.get(k).getBranchRateAverage()) {
            /* found new minimum; remember its index */
                    iMax = k;
                }
            }
            if(iMax != j) {
                Collections.swap(sortedByRate,j,iMax);
            }
        }
        return sortedByRate;
    }

    private List<Branch> sortByDistanceAndRate() {
        List<Branch> sortedByDistanceAndRate = branchList;
        List<Branch> sortedByDistance = sortByDistance();
        List<Branch> sortedByRate = sortByRate();
        Map<Branch, Double> distanceScore = new HashMap<>();
        Map<Branch, Double> rateScore = new HashMap<>();
        Map<Branch, Double> averageScore = new HashMap<>();

        //convert to percentages
        for (int i = 0; i < branchList.size();i++) {
            Branch currBranchByDistance = sortedByDistance.get(sortedByDistance.indexOf(branchList.get(i)));
            double distScore = (double)(branchList.size() - i)/branchList.size();
            distanceScore.put(currBranchByDistance, distScore);
            Log.d("distance score " + currBranchByDistance.getBranchName(), Double.toString(distScore));

            Branch currBranchByRate = sortedByRate.get(sortedByRate.indexOf(branchList.get(i)));
            double rteScore = (double)(branchList.size() - i)/branchList.size();
            rateScore.put(currBranchByRate, rteScore);
            Log.d("rate score " + currBranchByRate.getBranchName(), Double.toString(rteScore));
        }

        for (int j = 0; j < branchList.size();j++) {
            Branch currBranch = branchList.get(j);
            averageScore.put(currBranch, ((distanceScore.get(currBranch) + rateScore.get(currBranch))/2));
            Log.d(currBranch.getBranchName(), Double.toString((distanceScore.get(currBranch) + rateScore.get(currBranch))/2));
        }

        for (int k = 0; k < averageScore.size()-1; k++) {
        /* find the min element in the unsorted a[j .. n-1] */
        /* assume the min is the first element */
            int iMax = k;
        /* test against elements after j to find the smallest */
            for (int l = k+1; l < averageScore.size(); l++) {
            /* if this element is less, then it is the new minimum */
                if (averageScore.get(branchList.get(iMax)) < averageScore.get(branchList.get(l)) ) {
            /* found new minimum; remember its index */
                    iMax = l;
                }
            }
            if(iMax != k) {
                Collections.swap(sortedByDistanceAndRate,k,iMax);
            }
        }

        return sortedByDistanceAndRate;
    }
}
