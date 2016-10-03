package com.billmastervr;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.database.DatabaseHelper;
import com.stellago.stellago.LoginRegisterPage;
import com.stellago.stellago.R;

public class addMerchantPage extends AppCompatActivity {

    private EditText merchantName;
    private EditText merchantAmount;
    private Spinner merchantMonthSpinner;
    private CheckBox merchantStatusCheckBox;
    private Button merchantSubmitButton;
    private DatabaseHelper helper;
    private addMerchantTask merchantAddTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_merchant_page);

       helper = new DatabaseHelper(this);
       merchantName = (EditText)findViewById(R.id.merchantName);
       merchantAmount = (EditText)findViewById(R.id.merchantAmount);
       merchantMonthSpinner = (Spinner) findViewById(R.id.merchant_month_spinner);
       merchantStatusCheckBox = (CheckBox) findViewById(R.id.merchant_status);
       merchantSubmitButton = (Button)findViewById(R.id.merchant_add_button);
        merchantSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyAndEnterMerchantEntry();
            }
        });

    }

    //Add merchant validations
    public void verifyAndEnterMerchantEntry()
    {
        merchantName.setError(null);
        merchantAmount.setError(null);

        String merchant_name = merchantName.getText().toString();
        String merchant_Amount = merchantAmount.getText().toString();
        Double merchant_Amount_Value = 0.0;
        String merchant_Month = String.valueOf(merchantMonthSpinner.getSelectedItem());

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(merchant_name)) {
            merchantName.setError(getString(R.string.error_field_required));
            focusView = merchantName;
            cancel = true;
        }
        if (TextUtils.isEmpty(merchant_Amount)) {
            merchantAmount.setError(getString(R.string.error_field_required));
            focusView = merchantAmount;
            cancel = true;
        }else if (Double.parseDouble(merchant_Amount) < 0)
        {
            merchantAmount.setError("Amount should be greater than 0");
            focusView = merchantAmount;
            cancel = true;
        }else{
            merchant_Amount_Value = Double.parseDouble(merchant_Amount);
        }

        if (TextUtils.isEmpty(merchant_Month)) {
            TextView monthSpinnerView = (TextView) merchantMonthSpinner.getSelectedView();
            monthSpinnerView.setError(getString(R.string.error_field_required));
            focusView = merchantMonthSpinner.getSelectedView();
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();

        }else{
            Bill billToInsert = new Bill(merchant_name, merchant_Amount_Value, merchant_Month, merchantStatusCheckBox.isChecked());
            merchantAddTask = new addMerchantTask(billToInsert);
            merchantAddTask.execute((Void) null);
        }
    }

    public class addMerchantTask extends AsyncTask<Void, Void, Boolean> {

        private final Bill billToInsert;


        addMerchantTask(Bill bill) {
            this.billToInsert = bill;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return helper.addMerchant(billToInsert);

        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                merchantName.setText("");
                merchantAmount.setText("");

                Toast.makeText(getApplication(), R.string.success_merchant_addition, Toast.LENGTH_SHORT).show();
                Intent toBillMasterMain = new Intent(getApplicationContext(), BillMasterMain.class);
                startActivity(toBillMasterMain);
            }
        }

        @Override
        protected void onCancelled() {
            merchantAddTask = null;

        }
    }

}

