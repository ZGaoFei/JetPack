package com.example.jetpack.livedata.one_createlivedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class NameViewModel extends ViewModel {

    private MutableLiveData<String> name;
    private MutableLiveData<List<String>> nameList;

    public MutableLiveData<String> getCurrentName() {
        if (name == null) {
            name = new MutableLiveData<>();
         }
        return name;
    }

    public MutableLiveData<List<String>> getNameList() {
        if (nameList == null) {
            nameList = new MutableLiveData<>();
        }
        return nameList;
    }
}
