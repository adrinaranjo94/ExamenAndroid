package com.naranjo.adrian.examenandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.GenericTypeIndicator;
import com.naranjo.adrian.examenandroid.FirebaseObjects.FbDiscoteca;
import com.naranjo.adrian.examenandroid.SQLiteAdmin.DatabaseHandler;
import com.naranjo.adrian.examenandroid.SQLiteAdmin.SQLDiscoteca;

import java.util.ArrayList;
import java.util.Map;

public class NavDrawerActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    NavDrawerActivityEvents events;
    DrawerLayout drawer;

    TextView name_header,email_header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        databaseHandler = new DatabaseHandler(this);

        events = new NavDrawerActivityEvents(this);

        Dataholder.instance.firebaseAdmin.setListener(events);
        Dataholder.instance.firebaseAdmin.downloadBranch("Discotecas");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(events);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(events);

        //Aqui asignaremos los valores de nombre y correo del usuario al Navigation Drawer Header
        View hView =  navigationView.getHeaderView(0);
        name_header = hView.findViewById(R.id.name_nav_header);
        email_header = hView.findViewById(R.id.email_nav_header);

        //Si iniciamos sesion con Google
        if(Dataholder.instance.googleAccount !=null) {
            name_header.setText(Dataholder.instance.googleAccount.getDisplayName());
            email_header.setText(Dataholder.instance.googleAccount.getEmail());
        }
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

class NavDrawerActivityEvents implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,FirebaseAdmin.FireBaseAdminListener{

    NavDrawerActivity navDrawerActivity;
    ArrayList<FbDiscoteca> discos = new ArrayList<FbDiscoteca>();

    public NavDrawerActivityEvents(NavDrawerActivity navDrawerActivity) {
        this.navDrawerActivity = navDrawerActivity;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        navDrawerActivity.drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void fireBaseAdminUserConnected(boolean blconnected) {
    }

    @Override
    public void fireBaseAdminUserRegister(boolean blconnected) {
    }

    @Override
    public void fireBaseAdminBranchDownloaded(String branch, DataSnapshot dataSnapshot) {
        Log.v("MainActivity",branch+" --------"+ dataSnapshot);
        GenericTypeIndicator<Map<String,FbDiscoteca>> indicator = new GenericTypeIndicator<Map<String,FbDiscoteca>>(){};
        Map<String,FbDiscoteca> mDiscos= dataSnapshot.getValue(indicator);

        for (Map.Entry<String,FbDiscoteca> disco: mDiscos.entrySet()) {
            discos.add(disco.getValue());

            //Guarda la discoteca en SQLite
            Log.v("discoteca 2","nombre " + disco.getValue()._name);
            SQLDiscoteca sqlDiscoteca = new SQLDiscoteca(disco.getValue()._name,disco.getValue()._desc,disco.getValue()._lon,disco.getValue()._lat);
            navDrawerActivity.databaseHandler.addDisco(sqlDiscoteca);
        }

        ArrayList<SQLDiscoteca> sqlDiscotecas = navDrawerActivity.databaseHandler.getAllDiscotecas();
        for (SQLDiscoteca discoteca: sqlDiscotecas) {
            Log.v("discoteca","nombre " + discoteca.get_name());
        }
    }
}
