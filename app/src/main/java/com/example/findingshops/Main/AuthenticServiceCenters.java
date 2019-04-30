package com.example.findingshops.Main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.findingshops.Adapters.Authenticdataadapter;
import com.example.findingshops.Adapters.LocaldataAdapter;
import com.example.findingshops.Data.AuthenticBrands;
import com.example.findingshops.Data.LocalData;
import com.example.findingshops.R;
import com.example.findingshops.Utilities.API;
import com.example.findingshops.Utilities.RetrofitClient;

import java.io.IOException;
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

public class AuthenticServiceCenters extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<AuthenticBrands> authenticBrands;
    LocaldataAdapter localdataAdapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    int CacheSize = 10*1024*1024;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentic_service_centers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(AuthenticServiceCenters.this);
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

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://logisparktech.com/showroom/api/localAll.php/").client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        API apiService = retrofit.create(API.class);


        Call<List<LocalData>> call = RetrofitClient.getmInstance().getApi().getAuthenticdata();

        call.enqueue(new Callback<List<LocalData>>() {
            @Override
            public void onResponse(Call<List<LocalData>> call, Response<List<LocalData>> response) {

                List<LocalData> data = response.body();
                Authenticdataadapter authenticdataadapter = new Authenticdataadapter(getApplicationContext(),data);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
                recyclerView.setAdapter(authenticdataadapter);
                progressDialog.cancel();



            }

            @Override
            public void onFailure(Call<List<LocalData>> call, Throwable t) {
                Toast.makeText(AuthenticServiceCenters.this,"Something Went Wrong"+t.getMessage(),Toast.LENGTH_SHORT).show();

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
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.nav_local:
                Intent h= new Intent(AuthenticServiceCenters.this,LocalServiceCenters.class);
                startActivity(h);
                break;
            case R.id.nav_authentic:
                Intent i= new Intent(AuthenticServiceCenters.this,AuthenticServiceCenters.class);
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
