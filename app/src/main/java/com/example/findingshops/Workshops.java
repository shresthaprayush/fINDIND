package com.example.findingshops;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.findingshops.Adapters.RecycleViewHomeAdapter;
import com.example.findingshops.Data.Home_Data;
import com.example.findingshops.Main.AuthenticServiceCenters;
import com.example.findingshops.Main.LocalServiceCenters;
import com.example.findingshops.Main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class Workshops extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<Home_Data> lsthome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshops);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lsthome = new ArrayList<>();
        lsthome.add(new Home_Data("Local","Click here to list different Local Service Center",R.drawable.setting));
        lsthome.add(new Home_Data("Authentic","Click here to list different Authentic Service Center",R.drawable.setting));


        RecyclerView myhrv = (RecyclerView) findViewById(R.id.recycleviewhome);
        RecycleViewHomeAdapter myhAdapter = new RecycleViewHomeAdapter(this,lsthome);
        myhrv.setLayoutManager(new GridLayoutManager(this,1));
        myhrv.setAdapter(myhAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){

            case R.id.nav_local:
                Intent h= new Intent(Workshops.this, LocalServiceCenters.class);
                startActivity(h);
                break;
            case R.id.nav_authentic:
                Intent i= new Intent(Workshops.this, AuthenticServiceCenters.class);
                startActivity(i);
                break;
//            case R.id.nav_gallery:
//                Intent g= new Intent(Home.this,Gallery.class);
//                startActivity(g);
//                break;
//            case R.id.nav_slideshow:
//                Intent s= new Intent(Home.this,Slideshow.class);
//                startActivity(s);
//            case R.id.nav_tools:
//                Intent t= new Intent(Home.this,Tools.class);
//                startActivity(t);
//                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
