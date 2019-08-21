package com.example.jetpack.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jetpack.R;
import com.example.jetpack.viewmodel.test.TestViewModelFragment;

/**
 * 第一种使用ViewModelProviders获取viewmodel
 * of()方法中传入的上下文如果是同一个，将不会重复创建，会获取同一个viewmodel，
 * 方便在各个fragment中传值（ of(getActivity()) ）
 *
 * 第二种使用ViewModelProvider获取viewmodel
 * getInstance()方法中传入的是application对象，因此每次都会重复创建viewmodel
 *
 * ViewModelProviders内部也是使用ViewModelProvider实现，
 * 内部添加了当前fragment或者activity是否已经持有viewmodel对象的判断
 */
public class ViewModelActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;

    // 第一种viewmodel方式
    private CommunicateViewModel viewModel;
    // 第二种viewmodel方式
    private CommunicateViewModel viewModel1;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ViewModelActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model);

        initView();
        initFragment();
        initViewModel();
        initViewModel1();
    }

    private void initView() {
        Button button = findViewById(R.id.bt_change_data);
        button.setOnClickListener(this);
        textView = findViewById(R.id.tv_show_data);
    }

    private void initFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_left, LeftFragment.getInstance())
                .add(R.id.fragment_right, RightFragment.getInstance())
                .add(R.id.fragment_bottom, TestViewModelFragment.newInstance())
                .commit();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(CommunicateViewModel.class);
        viewModel.getName().observe(this, (String name) -> {
            textView.setText(name);
        });
    }

    private void initViewModel1() {
        viewModel1 = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(CommunicateViewModel.class);
        viewModel1.getName().observe(this, (String name)->{
            textView.setText(name);
        });

        // viewModel = new ViewModelProvider.NewInstanceFactory().create(CommunicateViewModel.class);
    }

    public CommunicateViewModel getViewModel() {
        if (viewModel1 != null) {
            return viewModel1;
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_change_data) {
            viewModel.setName("hello world! i am activity!");
//            viewModel1.setName("hello world! i am activity!");
        }
    }
}
