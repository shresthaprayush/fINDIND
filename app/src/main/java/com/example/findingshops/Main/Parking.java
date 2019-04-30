package com.example.findingshops.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.findingshops.Adapters.ParkingAdapter;
import com.example.findingshops.Data.LocalData;
import com.example.findingshops.Data.ParkingData;
import com.example.findingshops.R;
import com.example.findingshops.Utilities.RetrofitClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Parking extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    List<ParkingData> data;
    ParkingAdapter parkingAdapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ProgressDialog progressDialog = new ProgressDialog(Parking.this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setMessage("Please Wait we are fetching Data");
        progressDialog.show();

        recyclerView = findViewById(R.id.recycleviewparking);


        try {
            ServerSocket socket = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Call<List<ParkingData>> call = RetrofitClient.getmInstance().getApi().listparkingdata();

        call.enqueue(new Callback<List<ParkingData>>() {
            @Override
            public void onResponse(Call<List<ParkingData>> call, Response<List<ParkingData>> response) {
               data = response.body();

              parkingAdapter = new ParkingAdapter(getApplicationContext(),data);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(parkingAdapter);
                progressDialog.cancel();




            }

            @Override
            public void onFailure(Call<List<ParkingData>> call, Throwable t) {

                Toast.makeText(Parking.this,"Something Went Wrong"+t.getMessage(),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();


            }
        });



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
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id=item.getItemId();
        switch (id) {

            case R.id.nav_local:
                Intent h = new Intent(Parking.this, LocalServiceCenters.class);
                startActivity(h);
                break;
            case R.id.nav_authentic:
                Intent i = new Intent(Parking.this, AuthenticServiceCenters.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem menuItem = menu.findItem(R.id.serch);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

       s = s.toLowerCase();
        ArrayList<ParkingData> newlist = new ArrayList<>();

        for (ParkingData parkingDatalist : data ) {
            String nearlocation = parkingDatalist.getNearlocations().toLowerCase();
            String address = parkingDatalist.getAddress().toLowerCase();


            if ( address.contains(s) || nearlocation.contains(s)) {
                newlist.add(parkingDatalist);

            }

        }
       parkingAdapter.updatelist(newlist);

        return false;


    }
}
