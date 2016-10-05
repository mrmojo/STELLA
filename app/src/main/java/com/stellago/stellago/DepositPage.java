package com.stellago.stellago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class DepositPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_page);

        Spinner dropdown = (Spinner)findViewById(R.id.currency);
        String[] items = new String[]{"USD", "PHP"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        Button findBranchButton = (Button) findViewById(R.id.findBranchButton);
        findBranchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBranchPage = new Intent(getApplicationContext(), FindBranchPage.class);
                startActivity(toBranchPage);
            }
        });

        Button submitButton = (Button) findViewById(R.id.button2);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toConfirmationPage = new Intent(getApplicationContext(), ConfirmationPage.class);
                startActivity(toConfirmationPage);
            }
        });
    }
}
