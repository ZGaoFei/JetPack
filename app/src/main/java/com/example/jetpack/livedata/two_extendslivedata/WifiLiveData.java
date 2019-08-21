package com.example.jetpack.livedata.two_extendslivedata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.lang.ref.WeakReference;

public class WifiLiveData extends LiveData<Integer> {

    private static WifiLiveData myLiveData;

    private WeakReference<Context> weakReference;

    public static WifiLiveData getInstance(Context context) {
        if (myLiveData == null) {
            myLiveData = new WifiLiveData(context);
        }
        return myLiveData;
    }

    private WifiLiveData(Context context) {
        weakReference = new WeakReference<>(context);
    }

    @Override
    protected void onActive() {
        super.onActive();
        registerReceiver();
        Log.e("zgf", "=========on active==========");
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        unregisterReceiver();
        Log.e("zgf", "=========on inactive==========");
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        weakReference.get().registerReceiver(receiver, intentFilter);
    }

    private void unregisterReceiver() {
        weakReference.get().unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e("zgf", "============" + action);

            if (WifiManager.RSSI_CHANGED_ACTION.equals(action)) {
                int newRssi = intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, -200);
                int level = WifiManager.calculateSignalLevel(newRssi, 4);
                setValue(level);
            }
        }
    };

}
