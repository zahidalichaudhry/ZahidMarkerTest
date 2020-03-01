package com.example.zahidmarkertest.api;


import com.example.zahidmarkertest.model.LocationDataModel;
import com.example.zahidmarkertest.model.general.GeneralResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

;

public interface ApiService {
    @POST("/api/msDiscoverPage")
    @FormUrlEncoded
    Call<GeneralResponseModel<ArrayList<LocationDataModel>>> getMostpopularArticles(@Field("apiKey") String userName);

//    ?api-key=sKu1UKq4yi2G3UFYGs3z7QWiv4AUGuMO
//@Query("api-key") Strin   g apiKey
}
