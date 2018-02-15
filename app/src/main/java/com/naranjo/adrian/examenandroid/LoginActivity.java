package com.naranjo.adrian.examenandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    public Button bLogin, bRegister;
    public TextView tEmail, tPass;
    LoginActivityEvents events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        events = new LoginActivityEvents(this);

        bLogin = findViewById(R.id.bLogin);
        bRegister = findViewById(R.id.bRegister);

        tEmail = findViewById(R.id.tEmail);
        tPass = findViewById(R.id.tPass);

        bLogin.setOnClickListener(events);
        bRegister.setOnClickListener(events);


    }
}

class LoginActivityEvents implements View.OnClickListener{

    LoginActivity loginActivity;

    public LoginActivityEvents(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    public void onClick(View view) {

    }
}
