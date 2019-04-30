package com.example.findingshops.Main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import android.view.MenuItem;
import android.widget.Toast;

import com.example.findingshops.Adapters.LocaldataAdapter;
import com.example.findingshops.Data.LocalData;
import com.example.findingshops.R;
import com.example.findingshops.Utilities.RetrofitClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import android.support.v7.widget.SearchView;

import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocalServiceCenters extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SearchView.OnQueryTextListener {

    List<LocalData> localData;
    List<LocalData> data;
    LocaldataAdapter localdataAdapter;
    RecyclerView recyclerView;

    private String BaseUrl = "https://logisparktech.com/showroom/api/localAll.php/";
    long Cache = 10 * 1024 * 1024;


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localservicecenters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(LocalServiceCenters.this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setMessage("Please wait while we fetch the data");
        progressDialog.show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.recyclerview);


        try {
            ServerSocket socket = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Context context = null;

        //Building a okkhttpclient

        Cache cache = new Cache(context.getCacheDir(),Cache);



        OkHttpClient client = new OkHttpClient
                .Builder()
                .cache(new Cache(context.getCacheDir(), Cache)) // 10 MB
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (isNetworkAvailable()) {
                            request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                            Toast.makeText(LocalServiceCenters.this, "Network Available", Toast.LENGTH_LONG).show();
                        } else {
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                            Toast.makeText(LocalServiceCenters.this, "Network Not Available", Toast.LENGTH_LONG).show();

                        }
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Call<List<LocalData>> call = RetrofitClient.getmInstance().getApi().getlocaldata();
        call.enqueue(new Callback<List<LocalData>>() {
            @Override
            public void onResponse(Call<List<LocalData>> call, Response<List<LocalData>> response) {

                data = response.body();
                localdataAdapter = new LocaldataAdapter(getApplicationContext(), data);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(localdataAdapter);
                progressDialog.cancel();




            }

            @Override
            public void onFailure(Call<List<LocalData>> call, Throwable t) {

                Toast.makeText(LocalServiceCenters.this, "Something Went Wrong" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.cancel();

            }
        });


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.serch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);


        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_local:
                Intent h = new Intent(LocalServiceCenters.this, LocalServiceCenters.class);
                startActivity(h);
                break;
            case R.id.nav_authentic:
                Intent i = new Intent(LocalServiceCenters.this, AuthenticServiceCenters.class);
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        query = query.toLowerCase();
        ArrayList<LocalData> newlist = new ArrayList<>();

        for (LocalData localDatalist : data) {
            String name = localDatalist.getName().toLowerCase();


            if (name.contains(query)) {
                newlist.add(localDatalist);

            }

        }
        localdataAdapter.updatelist(newlist);

        return true;


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}


