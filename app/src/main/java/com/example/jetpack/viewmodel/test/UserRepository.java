package com.example.jetpack.viewmodel.test;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserRepository {

    /**
     * 需要对数据进行封装
     *
     * 获取数据返回结果，可能为状态为success或者failure
     * 需要将状态返回
     * 同时可能添加其他的操作，如缓存数据等
     *
     * 需要一个返回结果的来承载数据类型，返回的接口统一类型，
     * 因此在请求接口成功后需要对数据进行封装再操作
     *
     * 更多详情参考：
     * https://github.com/googlesamples/android-architecture-components/tree/88747993139224a4bb6dbe985adf652d557de621/GithubBrowserSample/app/src/main/java/com/android/example/github/repository
     */
    public Result<UserModel> getUser() {
        // TODO: 2019/8/21 模拟获取数据
        UserModel userModel = new UserModel();
        userModel.setName("zgfei");
        userModel.setAge(20);
        userModel.setSex("man");

        MutableLiveData<UserModel> liveData = new MutableLiveData<>();
        liveData.setValue(userModel);

        // success
        Result<UserModel> result = new Result<>();
        result.setData(liveData);

        // failure
        MutableLiveData<Error> errorMutableLiveData = new MutableLiveData<>();
        errorMutableLiveData.setValue(new Error("error", 400));
        result.setErrorLiveData(errorMutableLiveData);

        return result;
    }

    public LiveData<UserModel> getOtherUser() {
        UserModel userModel = new UserModel();
        userModel.setName("hello world");
        userModel.setAge(20);
        userModel.setSex("man");

        MutableLiveData<UserModel> liveData = new MutableLiveData<>();
        liveData.setValue(userModel);
        return liveData;
    }
}
