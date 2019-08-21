package com.example.jetpack.viewmodel.test;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViewModel extends ViewModel {
    private UserRepository userRepository;

    public TestViewModel() {
        userRepository = new UserRepository();
    }

    public Result<UserModel> getUserData() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository.getUser();
    }

    public MutableLiveData<UserModel> getOtherUserData() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return (MutableLiveData<UserModel>) userRepository.getOtherUser();
    }
}
