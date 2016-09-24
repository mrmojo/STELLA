package com.stellago.stellago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.billmastervr.billmastvr;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Button goButton = (Button) findViewById(R.id.goButton);
        Button vrButton = (Button) findViewById(R.id.VRbutton);
        goButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toGoPage = new Intent(getApplicationContext(), GoPage.class);
                startActivity(toGoPage);
            }
        });

        vrButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent toVRMode = new Intent(getApplicationContext(), billmastvr.class);
                startActivity(toVRMode);
            }
        });



    }
}
