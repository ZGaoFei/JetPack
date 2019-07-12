package com.example.jetpack.lifecycles.mvp;

import androidx.lifecycle.LifecycleOwner;

public class TestPresenter extends BasePresenter<TestPresenter.TestView> {

    public TestPresenter(LifecycleOwner owner) {
        super(owner);
    }

    public void getData() {
        if (getView() != null) {
            getView().update();
        }
    }

    public interface TestView extends IBaseView {
        void update();
    }
}
