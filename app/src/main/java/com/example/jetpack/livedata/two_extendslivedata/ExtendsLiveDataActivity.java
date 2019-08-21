package com.example.jetpack.livedata.two_extendslivedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jetpack.R;
import com.example.jetpack.livedata.one_createlivedata.CreateLiveDataActivity;

public class ExtendsLiveDataActivity extends AppCompatActivity {

    private TextView textView;

    private WifiViewModel wifiViewModel;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ExtendsLiveDataActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extends_live_data);
        textView = findViewById(R.id.tv_wifi);

        // 使用viewmodel结合livedata的方式
        wifiViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(WifiViewModel.class);
//        wifiViewModel.getWifiLiveData(this).observe(this, (Integer level)->
//            textView.setText("当前WiFi的等级为：" + level)
//        );

        // 单独使用livedata的方式
        WifiLiveData.getInstance(this).observe(this, (Integer level)->
                textView.setText("2当前WiFi的等级为：" + level)
        );
    }
}
