package com.example.jetpack.livedata.one_createlivedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jetpack.R;

import java.util.ArrayList;
import java.util.List;

public class CreateLiveDataActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvName;
    private Button btChangeName;
    private Button btChangeNameList;

    private NameViewModel nameViewModel;

    public static void start(Context context) {
        context.startActivity(new Intent(context, CreateLiveDataActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_live_data);

        tvName = findViewById(R.id.tv_show_name);
        btChangeName = findViewById(R.id.bt_change_name);
        btChangeNameList = findViewById(R.id.bt_change_name_list);
        btChangeName.setOnClickListener(this);
        btChangeNameList.setOnClickListener(this);

        nameViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(NameViewModel.class);
        nameViewModel.getCurrentName().observe(this, (String name) -> {
            if (!TextUtils.isEmpty(name)) {
                tvName.setText(name);
                Log.e("zgf", "=======name is update===============" + name);
            }
        });

        nameViewModel.getNameList().observe(this, (List<String> list) -> {
            for (int i = 0; i < list.size(); i++) {
                Log.e("zgf", "========name list is update===========" + list.get(i));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_change_name:
                changeName();
                break;
            case R.id.bt_change_name_list:
                changeNameList();
                break;
        }
    }

    private void changeName() {
        nameViewModel.getCurrentName().setValue("zgfei");
    }

    private void changeNameList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("zgfei " + i);
        }
        nameViewModel.getNameList().setValue(list);
    }
}
