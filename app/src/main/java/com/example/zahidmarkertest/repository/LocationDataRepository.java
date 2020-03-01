package com.example.zahidmarkertest.repository;

import android.app.Application;


import com.example.zahidmarkertest.api.ApiClientModule;
import com.example.zahidmarkertest.api.ApiUtils;
import com.example.zahidmarkertest.model.LocationDataModel;
import com.example.zahidmarkertest.model.general.GeneralResponseModel;
import com.example.zahidmarkertest.utils.IHandleAPICallBack;
import com.example.zahidmarkertest.utils.NetworkUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationDataRepository {
    private static final String TAG = "LocationDataRepository";
    private NetworkUtils network;
    private static LocationDataRepository instance = null;

    private Call<GeneralResponseModel<ArrayList<LocationDataModel>>> getPopularAticleList;
    private ApiClientModule apiClientModule = new ApiClientModule();


    private LocationDataRepository(Application application) {
        network = new NetworkUtils(application);
    }

    public static LocationDataRepository getInstance(Application application) {
        if (instance == null) {
            instance = new LocationDataRepository(application);
        }
        return instance;
    }

    public void getMostPopularArtiles(final IHandleAPICallBack handler) {

//        if (!network.isConnectedToInternet()) {
//            handler.onConnectionError();
//            return;
//        }
        try {

            getPopularAticleList = apiClientModule.getApiService().getMostpopularArticles(ApiUtils.APIKEY);

            getPopularAticleList.enqueue(new Callback<GeneralResponseModel<ArrayList<LocationDataModel>>>() {
                @Override
                public void onResponse(Call<GeneralResponseModel<ArrayList<LocationDataModel>>> call, Response<GeneralResponseModel<ArrayList<LocationDataModel>>> response) {
                    if (response.isSuccessful()) {
//                        if (response.body().getStatus() == "OK") {
                            handler.handleWebserviceCallBackSuccess(response);
//                        } else {
//                            handler.handleWebserviceCallBackFailure("Something went wrong");
//                        }
                    } else {
                        // Handle error returned from server
                        handler.handleWebserviceCallBackFailure(response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<GeneralResponseModel<ArrayList<LocationDataModel>>> call, Throwable t) {
                    t.printStackTrace();
                    handler.handleWebserviceCallBackFailure(t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            handler.handleWebserviceCallBackFailure(e.getMessage());

        }
    }

}
