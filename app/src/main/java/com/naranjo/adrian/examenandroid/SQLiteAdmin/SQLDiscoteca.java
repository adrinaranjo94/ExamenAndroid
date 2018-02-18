package com.naranjo.adrian.examenandroid.SQLiteAdmin;

/**
 * Created by adriannaranjosanchez on 18/2/18.
 */

public class SQLDiscoteca {
    int _id;
    String _name,_desc;
    Double _lon,_lat;

    public SQLDiscoteca() {
    }

    public SQLDiscoteca(int _id, String _name, String _desc, Double _lon, Double _lat) {
        this._id = _id;
        this._name = _name;
        this._desc = _desc;
        this._lon = _lon;
        this._lat = _lat;
    }

    public SQLDiscoteca(String _name, String _desc, Double _lon, Double _lat) {
        this._name = _name;
        this._desc = _desc;
        this._lon = _lon;
        this._lat = _lat;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_desc() {
        return _desc;
    }

    public void set_desc(String _desc) {
        this._desc = _desc;
    }

    public Double get_lon() {
        return _lon;
    }

    public void set_lon(Double _lon) {
        this._lon = _lon;
    }

    public Double get_lat() {
        return _lat;
    }

    public void set_lat(Double _lat) {
        this._lat = _lat;
    }
}
