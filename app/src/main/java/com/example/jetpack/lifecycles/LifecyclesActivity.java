package com.example.jetpack.lifecycles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jetpack.R;
import com.example.jetpack.lifecycles.mvp.TestActivity;

public class LifecyclesActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, LifecyclesActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycles);

        // getLifecycle().addObserver(new MyLifecycleObserver());
        // getLifecycle().addObserver(new MyGenericLifecycleObserver());

        findViewById(R.id.tv_mvp).setOnClickListener(view -> TestActivity.start(LifecyclesActivity.this));
    }
}
