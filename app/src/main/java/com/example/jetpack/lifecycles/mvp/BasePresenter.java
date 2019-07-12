package com.example.jetpack.lifecycles.mvp;


import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * presenter封装与Activity或者fragment同步的生命周期
 * <p>
 * 可以根据owner来判断当前activity或者fragment的对象
 */
public class BasePresenter<V extends IBaseView> implements IPresenter, FullLifecycleObserver {

    private LifecycleOwner owner;
    private Reference<V> mViewPreference;

    public BasePresenter(LifecycleOwner owner) {
        this.owner = owner;
        owner.getLifecycle().addObserver(new FullLifecycleObserverAdapter(owner, this));
    }

    protected boolean isViewAttached() {
        return mViewPreference != null && mViewPreference.get() != null;
    }

    protected V getView() {
        if (isViewAttached()) {
            return mViewPreference.get();
        } else {
            return null;
        }
    }

    protected void attachView(V view) {
        mViewPreference = new WeakReference<>(view);
    }

    protected void detachView() {
        if (mViewPreference != null) {
            mViewPreference.clear();
            mViewPreference = null;
        }
    }

    @Override
    public void onCreate(LifecycleOwner owner) {
        Log.e("zgf", "====onCreate====");
        if (owner instanceof IBaseView) {
            V tmpView = (V) owner;
            attachView(tmpView);
        } else {
            throw new RuntimeException("The class which used mvp must implements the interface "
                    + "that extends IBaseView");
        }
    }

    @Override
    public void onStart(LifecycleOwner owner) {
        Log.e("zgf", "====onStart====");
    }

    @Override
    public void onResume(LifecycleOwner owner) {
        Log.e("zgf", "====onResume====");
    }

    @Override
    public void onPause(LifecycleOwner owner) {
        Log.e("zgf", "====onPause====");
    }

    @Override
    public void onStop(LifecycleOwner owner) {
        Log.e("zgf", "====onStop====");
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        Log.e("zgf", "====onDestroy====");
        detachView();
    }
}
