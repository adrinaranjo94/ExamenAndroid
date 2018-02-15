package com.naranjo.adrian.examenandroid;

/**
 * Created by adriannaranjosanchez on 15/2/18.
 */

public class Dataholder {
    static public Dataholder instance=new Dataholder();
    public FirebaseAdmin firebaseAdmin;

    public Dataholder() {
        firebaseAdmin = new FirebaseAdmin();
    }

}
