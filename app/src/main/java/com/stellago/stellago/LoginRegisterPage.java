package com.stellago.stellago;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.database.DatabaseHelper;

public class LoginRegisterPage extends AppCompatActivity {

    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register_page);
        Log.i("JOREL", "LOGINREGISTER");
        helper = new DatabaseHelper(this);

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
                Intent toRegisterPage = new Intent(getApplicationContext(), RegisterPage.class);
                startActivity(toRegisterPage);
            }
        });

    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
