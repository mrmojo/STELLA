package com.stellago.stellago;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class DepositPage extends AppCompatActivity {
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_page);

        helper = new DatabaseHelper(this);
        final Spinner dropdown = (Spinner)findViewById(R.id.monthForPaymentSpinner);
        final List<Branch> branchList = helper.selectAllBranch();
        ArrayAdapter<Branch> adapter = new ArrayAdapter<Branch>(this, android.R.layout.simple_spinner_dropdown_item, branchList);
        dropdown.setAdapter(adapter);

        Button submitButton = (Button) findViewById(R.id.depositSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toConfirmationPage = new Intent(getApplicationContext(), ConfirmationPage.class);
                toConfirmationPage.putExtra("branchDetails", (Branch)dropdown.getSelectedItem());
                startActivity(toConfirmationPage);
            }
        });
    }
}
