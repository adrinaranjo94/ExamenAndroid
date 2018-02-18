package com.naranjo.adrian.examenandroid.SQLiteAdmin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by adriannaranjosanchez on 18/2/18.
 */

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    // Nombre de la BBDD
    private static final String DATABASE_NAME = "discotecasManager";

    // Nombre de la tabla Discotecas
    private static final String TABLE_DISCOS = "discotecas";

    //  Discotecas nombre de las columnas
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "description";
    private static final String KEY_LON = "longitude";
    private static final String KEY_LAT = "latitude";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Crea la tabla
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DISCOS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DESC + " TEXT," + KEY_LON+ " REAL,"+ KEY_LAT+" REAL" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Actualizando la BBDD
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Borra la tabla si ya existia
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCOS);

        // Crea la tabla
        onCreate(db);
    }

    public void addDisco(SQLDiscoteca SQLDiscoteca) {
        SQLiteDatabase dbW = this.getWritableDatabase();
        SQLiteDatabase dbR = this.getReadableDatabase();

        Cursor cursor = dbR.query(TABLE_DISCOS, new String[] { KEY_ID,
                        KEY_NAME, KEY_DESC, KEY_LON, KEY_LAT }, KEY_NAME + "=?",
                new String[] { String.valueOf(SQLDiscoteca.get_name()) }, null, null, null, null);
        if (cursor == null) {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, SQLDiscoteca.get_name()); // Nombre SQLDiscoteca
            values.put(KEY_DESC, SQLDiscoteca.get_desc()); // Descripcion SQLDiscoteca
            values.put(KEY_LON, SQLDiscoteca.get_lon()); // Longitud SQLDiscoteca
            values.put(KEY_LAT, SQLDiscoteca.get_lat()); // Latitud SQLDiscoteca

            // Insertar SQLDiscoteca
            dbW.insert(TABLE_DISCOS, null, values);
        }
        dbW.close(); // Cerrando conexion a la BBDD
    }

    public SQLDiscoteca getDisco(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DISCOS, new String[] { KEY_ID,
                        KEY_NAME, KEY_DESC, KEY_LON, KEY_LAT }, KEY_NAME + "=?",
                new String[] { String.valueOf(name) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        SQLDiscoteca disco = new SQLDiscoteca(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),  cursor.getDouble(3), cursor.getDouble(4));
        // Devuelve discotea
        return disco;
    }

    public ArrayList<SQLDiscoteca> getAllDiscotecas() {
        ArrayList<SQLDiscoteca> SQLDiscotecaList = new ArrayList<SQLDiscoteca>();
        // Query de seleccionar todas las discotecas
        String selectQuery = "SELECT  * FROM " + TABLE_DISCOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Bucle hasta que termine de recorrer lo obtenido en la query
        if (cursor.moveToFirst()) {
            do {
                SQLDiscoteca disco = new SQLDiscoteca();
                disco.set_id(Integer.parseInt(cursor.getString(0)));
                disco.set_name(cursor.getString(1));
                disco.set_desc(cursor.getString(2));
                disco.set_lon(Double.parseDouble(cursor.getString(3)));
                disco.set_lon(Double.parseDouble(cursor.getString(4)));
                // Añadiendo discoteca al ArrayList
                SQLDiscotecaList.add(disco);
            } while (cursor.moveToNext());
        }

        // Devuelve el Array de Discotecas
        return SQLDiscotecaList;
    }

    public int getDiscotecasCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DISCOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // Devuelve la cuenta total
        return cursor.getCount();
    }


}
