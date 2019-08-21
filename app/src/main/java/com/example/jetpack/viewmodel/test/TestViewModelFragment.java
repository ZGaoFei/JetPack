package com.example.jetpack.viewmodel.test;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jetpack.R;

/**
 * 测试使用viewmodel来获取数据
 * livedata监听数据变化的方式
 */
public class TestViewModelFragment extends Fragment implements View.OnClickListener {

    private TestViewModel mViewModel;

    private TextView tvName;
    private TextView tvSex;
    private TextView tvAge;

    public static TestViewModelFragment newInstance() {
        return new TestViewModelFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_view_model_fragment, container, false);

        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
    }

    private void initView(View view) {
        tvName = view.findViewById(R.id.tv_name);
        tvAge = view.findViewById(R.id.tv_age);
        tvSex = view.findViewById(R.id.tv_sex);

        Button btGetUserData = view.findViewById(R.id.bt_get_user);
        Button btGetOtherUserData = view.findViewById(R.id.bt_get_other_user);
        btGetUserData.setOnClickListener(this);
        btGetOtherUserData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_get_user:
                getUserData();
                break;
            case R.id.bt_get_other_user:
                getOtherUserData();
                break;
        }
    }

    private void getUserData() {
        Result<UserModel> userData = mViewModel.getUserData();
        userData.getData().observe(this, (UserModel user)->{
            tvName.setText("name: " + user.getName());
            tvSex.setText("sex: " + user.getSex());
            tvAge.setText("age: " + user.getAge());
        });
        userData.getErrorLiveData().observe(this, (Error error)->{
            // TODO: 2019/8/21 deal error
        });
    }

    private void getOtherUserData() {
        mViewModel.getOtherUserData().observe(this, (UserModel otherUser) -> {
            tvName.setText("name: " + otherUser.getName());
            tvSex.setText("sex: " + otherUser.getSex());
            tvAge.setText("age: " + otherUser.getAge());
        });
    }
}
