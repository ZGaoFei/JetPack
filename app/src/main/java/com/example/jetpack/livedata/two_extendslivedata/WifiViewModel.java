package com.example.jetpack.livedata.two_extendslivedata;

import android.content.Context;

import androidx.lifecycle.ViewModel;

public class WifiViewModel extends ViewModel {

    private WifiLiveData wifiLiveData;

    public WifiLiveData getWifiLiveData(Context context) {
        if (wifiLiveData == null) {
            wifiLiveData = WifiLiveData.getInstance(context);
        }
        return wifiLiveData;
    }
}
