package com.example.findingshops.Utilities;

import com.example.findingshops.Data.LocalData;
import com.example.findingshops.Data.ParkingData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {
 @GET("localAll.php/")
    Call<List<LocalData>> getlocaldata();

@GET("getDistinctCompany.php/")
    Call<List<LocalData>> getAuthenticdata();

@GET("getAllCompany.php/")
    Call<List<LocalData>> getspecificdata(@Query("company") String company);

@GET("getAllParkingSpots.php")
    Call<List<ParkingData>> listparkingdata();



}
