package com.example.jetpack.lifecycles.mvp;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

public class FullLifecycleObserverAdapter implements LifecycleObserver {

    private LifecycleOwner owner;
    private FullLifecycleObserver observer;

    public FullLifecycleObserverAdapter(LifecycleOwner owner, FullLifecycleObserver observer) {
        this.owner = owner;
        this.observer = observer;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        observer.onCreate(owner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        observer.onStart(owner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        observer.onResume(owner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        observer.onPause(owner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        observer.onStop(owner);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        observer.onDestroy(owner);
    }

}
