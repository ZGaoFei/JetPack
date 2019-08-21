package com.example.jetpack.viewmodel;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jetpack.R;


public class LeftFragment extends Fragment implements View.OnClickListener {

    private TextView textView;

    private CommunicateViewModel viewModel;
    private CommunicateViewModel viewModel1;

    public LeftFragment() {
    }

    public static LeftFragment getInstance() {
        return new LeftFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(CommunicateViewModel.class);
        Log.e("zgf", "=========left===========" + viewModel);
        viewModel.getName().observe(getActivity(), (String name) -> {
            textView.setText(name);
        });

//        getViewModel();
//        viewModel1.getName().observe(this, (String name) ->
//                textView.setText(name)
//        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Button button = view.findViewById(R.id.bt_change_data_left);
        button.setOnClickListener(this);
        textView = view.findViewById(R.id.tv_show_data_left);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_change_data_left) {
            viewModel.setName("left fragment to right fragment!");
//            viewModel1.setName("hello world! i am is left fragment!");
        }
    }

    private void getViewModel() {
        viewModel1 = ((ViewModelActivity) getActivity()).getViewModel();
    }

}
