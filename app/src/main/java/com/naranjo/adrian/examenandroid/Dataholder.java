package com.naranjo.adrian.examenandroid;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by adriannaranjosanchez on 15/2/18.
 */

public class Dataholder {
    static public Dataholder instance=new Dataholder();
    public FirebaseAdmin firebaseAdmin;
    public GoogleSignInAccount googleAccount;

    public Dataholder() {
        firebaseAdmin = new FirebaseAdmin();
    }

}
