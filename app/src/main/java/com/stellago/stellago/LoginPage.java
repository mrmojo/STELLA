package com.stellago.stellago;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        final Button button = (Button) findViewById(R.id.loginButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final TextView userNameTextView = (TextView) findViewById(R.id.userName);
                final TextView passwordTextView = (TextView) findViewById(R.id.password);
                final TextView topTextView = (TextView) findViewById(R.id.topTextView);
                String userName = userNameTextView.getText().toString();
                String password = passwordTextView.getText().toString();
                if ("stellago".equals(userName) && "stellago".equals(password)) {
                    Intent toMainPage = new Intent(LoginPage.this, MainPage.class);
                    LoginPage.this.startActivity(toMainPage);
                } else {
                    topTextView.setText("INVALID");
                }
            }
        });

    }
}
