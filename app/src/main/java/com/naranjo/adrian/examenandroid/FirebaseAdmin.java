package com.naranjo.adrian.examenandroid;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by adriannaranjosanchez on 15/2/18.
 */

public class FirebaseAdmin {
    private FirebaseAuth mAuth;
    private FireBaseAdminListener listener;
    FirebaseUser currentUser;

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void onCreate(){
        mAuth = FirebaseAuth.getInstance();
    }

    public void setListener(FireBaseAdminListener listener) {
        this.listener = listener;
    }

    public FirebaseAdmin(){
        mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public void registerWithUserAndPassword (final String email, final String password, Activity activity) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");

                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseAdmin.this.listener.fireBaseAdminUserConnected(true);

                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                            //        Toast.LENGTH_SHORT).show();
                            FirebaseAdmin.this.listener.fireBaseAdminUserConnected(false);
                        }

                        // ...
                    }
                });
    }

    public void signIn(String email, String password,Activity activity) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseAdmin.this.listener.fireBaseAdminUserRegister(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            FirebaseAdmin.this.listener.fireBaseAdminUserRegister(false);
                        }

                        // ...
                    }
                });

    }


    public interface FireBaseAdminListener{

        public void fireBaseAdminUserConnected(boolean blconnected);
        public void fireBaseAdminUserRegister(boolean blconnected);
    }

}



