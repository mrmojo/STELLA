package com.stellago.stellago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.billmastervr.BillMasterMain;
import com.billmastervr.billmastvr;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Button goButton = (Button) findViewById(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toGoPage = new Intent(getApplicationContext(), GoPage.class);
                startActivity(toGoPage);
            }
        });

        Button vrButton = (Button) findViewById(R.id.VRButton);
        vrButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toVRMode = new Intent(getApplicationContext(), BillMasterMain.class);
                startActivity(toVRMode);
            }
        });

        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toFirstPage = new Intent(getApplicationContext(), LoginRegisterPage.class);
                startActivity(toFirstPage);
            }
        });
    }
}
