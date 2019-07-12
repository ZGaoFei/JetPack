package com.example.jetpack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.example.jetpack.lifecycles.LifecyclesActivity;

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

        findViewById(R.id.livedata).setOnClickListener((view) -> {

        });
        findViewById(R.id.navigation).setOnClickListener((view) -> {
        });
        findViewById(R.id.paging).setOnClickListener((view) -> {
        });
        findViewById(R.id.viewmodel).setOnClickListener((view) -> {
        });
        findViewById(R.id.workmanager).setOnClickListener((view) -> {
        });
        findViewById(R.id.camerax).setOnClickListener((view) -> {
        });
        findViewById(R.id.room).setOnClickListener((view) -> {
        });
    }
}
