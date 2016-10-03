package com.billmastervr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.database.DatabaseHelper;
import com.stellago.stellago.R;

import java.lang.reflect.Array;
import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class viewBillByMonths extends AppCompatActivity {

    private Button vBillViewButton;
    private Spinner startingMonthSpinner;
    private Spinner endingMonthSpinner;
    private CheckBox vBillstatus;
    private String billStatus;
    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill_by_months);

        startingMonthSpinner = (Spinner) findViewById(R.id.vbill_Starting_Month_spinner);
        endingMonthSpinner = (Spinner) findViewById(R.id.vbill_Ending_Month_spinner);
        vBillstatus = (CheckBox) findViewById(R.id.vbillStatus);
        vBillViewButton = (Button)findViewById(R.id.vbill_View_Button);
        vBillViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(vBillstatus.isChecked())
                {
                    billStatus = "Y";
                }else{
                    billStatus = "N";
                }

                int startingMonthNumber = getMonthNumberByString(startingMonthSpinner.getSelectedItem().toString());
                int endingMonthNumber =  getMonthNumberByString(endingMonthSpinner.getSelectedItem().toString());
                ArrayList<String> billList = getMonthsToView(startingMonthNumber, endingMonthNumber);
                //Execute query to find bills by date
                ArrayList<Bill> retrievedBillList = helper.selectBillsForDiffMonth(billList, billStatus);

                //do something with data

                Intent toVBillVR = new Intent(getApplicationContext(), billmastvr.class);
                startActivity(toVBillVR);
            }
        });
    }

    //Gets month number based on String passed
    public int getMonthNumberByString (String month)
    {
        int monthNumber = 0;
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        for(int i=0; i < months.length; i++)
        {
            if(months[i].equals(month)){
                monthNumber = i;
            }
        }
        return monthNumber;
    }
    //Get numbers first then translate to Strings
    public ArrayList<String> getMonthsToView(int startingMonth, int endingMonth)
    {
        ArrayList<String> monthList = new ArrayList<String>();
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        for(int i=startingMonth; i <= endingMonth; i++)
        {
            monthList.add(months[i]);
            Log.d("Month with index " + i,months[i]);
        }
        return monthList;
    }

}
