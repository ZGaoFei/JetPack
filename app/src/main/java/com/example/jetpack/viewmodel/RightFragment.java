package com.example.jetpack.viewmodel;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jetpack.R;
import com.example.jetpack.viewmodel.test.TestViewModel;

public class RightFragment extends Fragment implements View.OnClickListener {

    private TextView textView;

    private CommunicateViewModel viewModel;
    private CommunicateViewModel viewModel1;

    public RightFragment() {
    }

    public static RightFragment getInstance() {
        return new RightFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(CommunicateViewModel.class);
        Log.e("zgf", "=========right===========" + viewModel);
        viewModel.getName().observe(getActivity(), (String name) -> {
            textView.setText(name);
        });

//        getViewModel();
//        viewModel1.getName().observe(getActivity(), (String name) ->
//                textView.setText(name)
//        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Button button = view.findViewById(R.id.bt_change_data_right);
        button.setOnClickListener(this);
        textView = view.findViewById(R.id.tv_show_data_right);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_change_data_right) {
            viewModel.setName("right fragment to left fragment!");
//            viewModel1.setName("hello world! i am right fragment!");
        }
    }

    private void getViewModel() {
        viewModel1 = ((ViewModelActivity) getActivity()).getViewModel();
    }


}
