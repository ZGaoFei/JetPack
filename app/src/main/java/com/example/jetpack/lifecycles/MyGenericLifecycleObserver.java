package com.example.jetpack.lifecycles;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

@SuppressLint("RestrictedApi")
public class MyGenericLifecycleObserver implements GenericLifecycleObserver {

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                Log.e("zgf", "==MyGenericLifecycleObserver====ON_CREATE=======");
                break;
            case ON_START:
                Log.e("zgf", "==MyGenericLifecycleObserver====ON_START=======");
                break;
            case ON_RESUME:
                Log.e("zgf", "==MyGenericLifecycleObserver====ON_RESUME=======");
                break;
            case ON_PAUSE:
                Log.e("zgf", "==MyGenericLifecycleObserver====ON_PAUSE=======");
                break;
            case ON_STOP:
                Log.e("zgf", "==MyGenericLifecycleObserver====ON_STOP=======");
                break;
            case ON_DESTROY:
                Log.e("zgf", "==MyGenericLifecycleObserver====ON_DESTROY=======");
                break;
            case ON_ANY:
                throw new IllegalArgumentException("ON_ANY must not been send by anybody");
        }
    }
}
