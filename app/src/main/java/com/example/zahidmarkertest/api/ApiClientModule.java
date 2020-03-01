package com.example.zahidmarkertest.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClientModule {

    public ApiService getApiService() {

        Gson gson = new GsonBuilder().create();

        Retrofit apiClient = new Retrofit.Builder()
                .baseUrl(ApiUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return apiClient.create(ApiService.class);

    }
}
