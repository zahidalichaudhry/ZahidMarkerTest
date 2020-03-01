package com.example.zahidmarkertest.model.general;

import com.example.zahidmarkertest.model.LocationDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GeneralResponseModel<T> implements Serializable {



    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("status")
    @Expose

    private String status;

    @SerializedName("locationData")
    private T data;


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
