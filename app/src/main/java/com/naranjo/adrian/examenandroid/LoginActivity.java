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

        //Inicializamos el objeto events que se encargara de todos los eventos en el activity
        events = new LoginActivityEvents(this);

        //Asignamos listener al objeto Firebase que se encuentra en el Dataholder
        Dataholder.instance.firebaseAdmin.setListener(events);

        //Anclado elementos visuales con objetos
        bLogin = findViewById(R.id.bLogin);
        bRegister = findViewById(R.id.bRegister);

        tEmail = findViewById(R.id.tEmail);
        tPass = findViewById(R.id.tPass);

        //Asignar listener a estos objetos
        bLogin.setOnClickListener(events);
        bRegister.setOnClickListener(events);


    }
}

class LoginActivityEvents implements View.OnClickListener,FirebaseAdmin.FireBaseAdminListener{

    LoginActivity loginActivity;

    public LoginActivityEvents(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bLogin) {
            Dataholder.instance.firebaseAdmin.signIn(loginActivity.tEmail.getText().toString(), loginActivity.tPass.getText().toString(),loginActivity);
        }
    }

    @Override
    public void fireBaseAdminUserConnected(boolean blconnected) {
        if (blconnected) {
            //LOGIN CORRECTO
        }
    }

    @Override
    public void fireBaseAdminUserRegister(boolean blconnected) {

    }
}
