package com.example.jetpack.viewmodel.test;

import androidx.lifecycle.LiveData;

public class Result<T> {
    private LiveData<T> liveData;
    private LiveData<Error> errorLiveData;

    public LiveData<T> getData() {
        if (liveData != null) {
            return liveData;
        }
        return null;
    }

    public void setData(LiveData<T> liveData) {
        this.liveData = liveData;
    }

    public LiveData<Error> getErrorLiveData() {
        if (errorLiveData != null) {
            return errorLiveData;
        }
        return null;
    }

    public void setErrorLiveData(LiveData<Error> errorLiveData) {
        this.errorLiveData = errorLiveData;
    }

}
