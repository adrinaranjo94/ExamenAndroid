package com.naranjo.adrian.examenandroid.FirebaseObjects;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by adriannaranjosanchez on 18/2/18.
 */
@IgnoreExtraProperties
public class FbDiscoteca {
    public String _name;
    public String _desc;
    public double _lon;
    public double _lat;

    public FbDiscoteca() {
    }

    public FbDiscoteca(String _name, String _desc, double _lon, double _lat) {
        this._name = _name;
        this._desc = _desc;
        this._lon = _lon;
        this._lat = _lat;
    }
}
