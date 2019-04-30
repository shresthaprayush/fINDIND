package com.example.findingshops.Main;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.SearchView;

import android.view.MenuItem;
import android.widget.Button;

import com.example.findingshops.Adapters.RecycleViewHomeAdapter;
import com.example.findingshops.Data.Home_Data;

import com.example.findingshops.Data.LocalData;
import com.example.findingshops.R;
import com.example.findingshops.Utilities.API;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    List<Home_Data> lsthome;
    RecycleViewHomeAdapter myhAdapter;
  Toolbar toolbar = null;
  Button buttonmap;
    int CacheSize = 10*1024*1024;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);




        setSupportActionBar(toolbar);


        lsthome = new ArrayList<>();
        lsthome.add(new Home_Data("Workshops","Click here to list different Local Service Center",R.drawable.setting));
        lsthome.add(new Home_Data("Parking Spots","Click here to list different Authentic Service Center",R.drawable.park));
        lsthome.add(new Home_Data("Petrol Pumps","Click here to list different Authentic Service Center",R.drawable.gas));

        RecyclerView myhrv = (RecyclerView) findViewById(R.id.recycleviewhome);
        myhAdapter = new RecycleViewHomeAdapter(this,lsthome);
        myhrv.setLayoutManager(new GridLayoutManager(this,1));
        myhrv.setAdapter(myhAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Cache cache = new Cache(getCacheDir(), CacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Interceptor.Chain chain)
                            throws IOException {
                        Request request = chain.request();
                        if (!isNetworkAvailable()) {
                            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale \
                            request = request
                                    .newBuilder()
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://logisparktech.com/showroom/api/").client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        API apiService = retrofit.create(API.class);

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




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.nav_local:
                Intent h= new Intent(MainActivity.this,LocalServiceCenters.class);
                startActivity(h);
                break;
            case R.id.nav_authentic:
                Intent i= new Intent(MainActivity.this,AuthenticServiceCenters.class);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }





}
