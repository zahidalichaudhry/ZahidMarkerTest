package com.example.zahidmarkertest.utils;

public class Resource<T> {
    private Status.status mStatus;
    private T data;
    String message;

    public Resource(Status.status mStatus, T data, String message) {
        this.mStatus = mStatus;
        this.data = data;
        this.message = message;
    }

    public Status.status getStatus() {
        return mStatus;
    }

    public void setStatus(Status.status mStatus) {
        this.mStatus = mStatus;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}