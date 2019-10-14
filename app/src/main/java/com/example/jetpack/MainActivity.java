package com.example.jetpack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.example.jetpack.downloadmanager.DownloadActivity;
import com.example.jetpack.lifecycles.LifecyclesActivity;
import com.example.jetpack.livedata.one_createlivedata.CreateLiveDataActivity;
import com.example.jetpack.livedata.two_extendslivedata.ExtendsLiveDataActivity;
import com.example.jetpack.viewmodel.ViewModelActivity;
import com.example.jetpack.workmanager.WorkManagerActivity;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        findViewById(R.id.lifecycles).setOnClickListener(view ->
                LifecyclesActivity.start(context)
        );

        findViewById(R.id.livedata_create).setOnClickListener((view) -> {
            CreateLiveDataActivity.start(context);
        });
        findViewById(R.id.livedata_extends).setOnClickListener((view) -> {
            ExtendsLiveDataActivity.start(context);
        });

        findViewById(R.id.navigation).setOnClickListener((view) -> {
        });
        findViewById(R.id.paging).setOnClickListener((view) -> {
        });

        findViewById(R.id.viewmodel).setOnClickListener((view) -> {
            ViewModelActivity.start(context);
        });
        findViewById(R.id.workmanager).setOnClickListener((view) -> {
            WorkManagerActivity.start(context);
        });

        findViewById(R.id.camerax).setOnClickListener((view) -> {
        });
        findViewById(R.id.room).setOnClickListener((view) -> {
        });

        findViewById(R.id.download).setOnClickListener((view) -> {
            DownloadActivity.start(context);
        });
    }
}
