package com.billmastervr;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.database.DatabaseHelper;
import com.stellago.stellago.GoPage;
import com.stellago.stellago.MainPage;
import com.stellago.stellago.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BillMasterMain extends AppCompatActivity {

    private DatabaseHelper helper;
    private String billStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_master_main);
        helper = new DatabaseHelper(this);
        Button addMerchantButton = (Button) findViewById(R.id.addMerchant);
        addMerchantButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent addMerchantPage = new Intent(getApplicationContext(), addMerchantPage.class);
                startActivity(addMerchantPage);
            }
        });
        Button viewPaidThisMonth = (Button) findViewById(R.id.paidThisMonth);
        viewPaidThisMonth.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                ArrayList<Bill> retrievedBillList = new ArrayList<Bill>();
                retrievedBillList = getBillsForCurrentMonth("Y");

            }
        });
        Button viewUnPaidThisMonth = (Button) findViewById(R.id.unpaidThisMonth);
        viewUnPaidThisMonth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ArrayList<Bill> retrievedBillList = new ArrayList<Bill>();
                retrievedBillList = getBillsForCurrentMonth("N");
            }
        });
        Button viewBillByMonths = (Button) findViewById(R.id.billByMonths);
        viewBillByMonths.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toVBillPage = new Intent(getApplicationContext(), viewBillByMonths.class);
                startActivity(toVBillPage);
            }
        });
        Button billMasterMainBack = (Button) findViewById(R.id.billMasterMainBack);
        billMasterMainBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toMainPage = new Intent(getApplicationContext(), MainPage.class);
                startActivity(toMainPage);
            }
        });
    }

    //get by status
    public ArrayList<Bill> getBillsForCurrentMonth(String billStatus)
    {
        DateFormat dateFormat = new SimpleDateFormat("MMMM");
        Date date = new Date();
        Log.d("Current Month = ", dateFormat.format(date).toString());

        ArrayList<Bill> retrievedBillList = new ArrayList<Bill>();
        retrievedBillList = helper.selectBillsForCurrentMonth(dateFormat.format(date).toString(), billStatus);

        return retrievedBillList;
    }

}
