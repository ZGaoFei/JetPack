package com.example.jetpack.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CommunicateViewModel extends ViewModel {

    private MutableLiveData<String> liveData;

    public MutableLiveData<String> getName() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }

    public void setName(String name) {
        if (liveData != null) {
            liveData.setValue(name);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        liveData = null;
    }
}
