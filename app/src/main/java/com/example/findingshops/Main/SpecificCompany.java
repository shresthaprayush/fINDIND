package com.example.findingshops.Main;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findingshops.Adapters.LocaldataAdapter;
import com.example.findingshops.Adapters.Specificdataadapter;
import com.example.findingshops.Data.LocalData;
import com.example.findingshops.R;
import com.example.findingshops.Utilities.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecificCompany extends AppCompatActivity implements SearchView.OnQueryTextListener, android.support.v7.widget.SearchView.OnQueryTextListener {

    View view;
    List<LocalData> localData;
    private RecyclerView recyclerView;
    String company,color;
    TextView textViewcompanyname;
    Specificdataadapter specificdataadapter;


    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_company);

        recyclerView = findViewById(R.id.recyclerviewspecific);
        //textViewcompanyname = findViewById(R.id.texttitlecompany);
        view = this.getWindow().getDecorView();
        progressDialog = new ProgressDialog(SpecificCompany.this);
        progressDialog.setTitle("Fetching Data");
        progressDialog.setMessage("Please wait while we fetch the data");
        progressDialog.show();


        company = getIntent().getExtras().getString("Companyname");
        color = getIntent().getExtras().getString("Color");

        getSupportActionBar().setTitle(company +" Service Centers");

        Call<List<LocalData>> call = RetrofitClient.getmInstance().getApi().getspecificdata(company);

        call.enqueue(new Callback<List<LocalData>>() {
            @Override
            public void onResponse(Call<List<LocalData>> call, Response<List<LocalData>> response) {
                 localData = response.body();

                specificdataadapter = new Specificdataadapter(getApplicationContext(),localData);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(specificdataadapter);
                progressDialog.cancel();
            }

            @Override
            public void onFailure(Call<List<LocalData>> call, Throwable t) {
                Toast.makeText(SpecificCompany.this,"Something Wrong"+t.getMessage(),Toast.LENGTH_SHORT);
                progressDialog.cancel();

            }
        });
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();
        ArrayList<LocalData> newlist = new ArrayList<>();

        for (LocalData localDatalist : localData ) {
            String name = localDatalist.getNearlocations().toLowerCase();
            String address = localDatalist.getAddress().toLowerCase();


            if (name.contains(newText) || address.contains(newText)) {
                newlist.add(localDatalist);

            }

        }
        specificdataadapter.updatelist(newlist);

        return true;

    }
}
