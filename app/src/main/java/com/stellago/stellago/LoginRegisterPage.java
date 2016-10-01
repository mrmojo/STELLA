package com.stellago.stellago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginRegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register_page);

        Button toLogin = (Button) findViewById(R.id.toLogin);
        toLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent toLoginPage = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(toLoginPage);
            }
        });

        Button toRegister = (Button) findViewById(R.id.toRegister);
        toRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent toRegisterPage = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(toRegisterPage);
            }
        });

    }
}
