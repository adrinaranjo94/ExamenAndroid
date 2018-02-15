package com.naranjo.adrian.examenandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

    public Button bLogin, bRegister;
    public TextView tEmail, tPass;
    LoginActivityEvents events;
    CallbackManager callbackManager;
    LoginButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializar Facebook
        callbackManager = CallbackManager.Factory.create();

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


        callbackManager = CallbackManager.Factory.create();



        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList
                ("public_profile","email"));
        // If using in a fragment
        //loginButton.setFragment(this);

        // Callback registration



        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.v("Correcto","Metodo OnSuccess");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
            }
        });

        /*if(Dataholder.instance.firebaseAdmin.getmAuth()!=null){
            events.iniciarAppSecondActivity();


        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);

    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FirebaseFacebook", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Dataholder.instance.firebaseAdmin.getmAuth().signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this,  new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Logueo", "signInWithCredential:success");
                            FirebaseUser user = Dataholder.instance.firebaseAdmin.getmAuth().getCurrentUser();
                            events.iniciarNavDrawer();
                            //updateUI(user);
                        } else {
                                   /* Intent myIntent = new Intent(this, FacebookActivity.class);
                                    startActivity(myIntent);
                                    */
                            // If sign in fails, display a message to the user.
                            Log.w("Logueo", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

}

class LoginActivityEvents implements View.OnClickListener,FirebaseAdmin.FireBaseAdminListener{

    public LoginActivity loginActivity;

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

    }

    @Override
    public void fireBaseAdminUserRegister(boolean blconnected) {
        if (blconnected) {
            Intent intent = new Intent(this.loginActivity,NavDrawerActivity.class);
            this.loginActivity.startActivity(intent);
            this.loginActivity.finish();
        }
    }

    public void iniciarNavDrawer(){
        Intent intent = new Intent(this.loginActivity,NavDrawerActivity.class);
        this.loginActivity.startActivity(intent);
        this.loginActivity.finish();
    }
}
