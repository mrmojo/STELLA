package com.stellago.stellago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GoPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_page);

        Button depositButton = (Button) findViewById(R.id.depositButton);

        depositButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toDepositPage = new Intent(getApplicationContext(), DepositPage.class);
                startActivity(toDepositPage);
            }
        });
    }
}
