package com.stellago.stellago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ConfirmationPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);
        Intent intent = getIntent();
        final Branch details = intent.getExtras().getParcelable("branchDetails");

        Button checkDirections = (Button) findViewById(R.id.CheckDirections);
        checkDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDirectionsPage = new Intent(getApplicationContext(), FindBranchPage.class);
                toDirectionsPage.putExtra("latitude",details.getBranchLatitude());
                toDirectionsPage.putExtra("longitude",details.getBranchLongitude());
                startActivity(toDirectionsPage);
            }
        });
    }
}
