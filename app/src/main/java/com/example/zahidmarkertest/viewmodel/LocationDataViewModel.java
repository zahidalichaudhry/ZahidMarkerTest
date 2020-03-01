package com.example.zahidmarkertest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.zahidmarkertest.model.LocationDataModel;
import com.example.zahidmarkertest.model.general.GeneralResponseModel;
import com.example.zahidmarkertest.repository.LocationDataRepository;
import com.example.zahidmarkertest.utils.IHandleAPICallBack;
import com.example.zahidmarkertest.utils.Resource;
import com.example.zahidmarkertest.utils.Status;

import java.util.ArrayList;

import retrofit2.Response;

public class LocationDataViewModel extends AndroidViewModel {
    private LocationDataRepository locationDataRepository;

    private MutableLiveData<Resource<GeneralResponseModel<ArrayList<LocationDataModel>>>> getLocationsMutData;



    public LocationDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        if(locationDataRepository == null) {
            locationDataRepository = LocationDataRepository.getInstance(getApplication());
        }
        if (getLocationsMutData == null) {
            getLocationsMutData = new MutableLiveData<>();
        }
    }
    public MutableLiveData<Resource<GeneralResponseModel<ArrayList<LocationDataModel>>>> getLocationsData() {
        return getLocationsMutData;
    }


    public void getLocationDataAPI() {
        getLocationsMutData.setValue(new Resource<GeneralResponseModel<ArrayList<LocationDataModel>>>(Status.status.LOADING,
                null, "Loading"));
        locationDataRepository.getMostPopularArtiles(
                new IHandleAPICallBack() {
                    @Override
                    public void handleWebserviceCallBackSuccess(Response response) {
                        Response<GeneralResponseModel<ArrayList<LocationDataModel>>> mResponse = response;
                        getLocationsMutData.setValue(new Resource<GeneralResponseModel<ArrayList<LocationDataModel>>>(Status.status.SUCCESS,
                                mResponse.body(), "Success"));
                    }

                    @Override
                    public void handleWebserviceCallBackFailure(String error) {
                        getLocationsMutData.setValue(new Resource<GeneralResponseModel<ArrayList<LocationDataModel>>>(Status.status.ERROR,
                                null, error));
                    }

                    @Override
                    public void onConnectionError() {
                        getLocationsMutData.setValue(new Resource<GeneralResponseModel<ArrayList<LocationDataModel>>>(Status.status.ERROR,
                                null, "Connection Error"));
                    }
                });
    }

}
