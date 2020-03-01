package com.example.zahidmarkertest.utils;

import retrofit2.Response;

public interface IHandleAPICallBack {
    void handleWebserviceCallBackSuccess(Response response);
    void handleWebserviceCallBackFailure(String error);
    void onConnectionError();
}
