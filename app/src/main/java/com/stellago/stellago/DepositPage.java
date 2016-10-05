package com.stellago.stellago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.database.DatabaseHelper;

import java.util.ArrayList;

public class DepositPage extends AppCompatActivity {
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_page);

        helper = new DatabaseHelper(this);
        Spinner dropdown = (Spinner)findViewById(R.id.monthForPaymentSpinner);
        ArrayList<Branch> branchList = helper.selectAllBranch();
        String [] branchNames = new String[branchList.size()];
        for (int i = 0; i < branchList.size(); i++) {
            branchNames[i] = branchList.get(i).getBranchName().concat(" Rate = ").concat(branchList.get(i).getBranchRateString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, branchNames);
        dropdown.setAdapter(adapter);

        Button submitButton = (Button) findViewById(R.id.depositSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toDepositPage = new Intent(getApplicationContext(), DepositPage.class);
                startActivity(toDepositPage);
            }
        });
    }
}
