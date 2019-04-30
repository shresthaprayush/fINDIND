package com.example.findingshops.Utilities;

import retrofit2.Retrofit;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

    public class RetrofitClient {

        private static final String baseurl="https://logisparktech.com/showroom/api/";
        private static RetrofitClient mInstance;
        private Retrofit retrofit;


        private RetrofitClient(){

            retrofit = new Retrofit.Builder().baseUrl(baseurl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        public static  synchronized RetrofitClient getmInstance(){
            if (mInstance == null){
                mInstance = new RetrofitClient();
            }

            return mInstance;
        }



        public API getApi(){
            return retrofit.create(API.class);

        }

    }



