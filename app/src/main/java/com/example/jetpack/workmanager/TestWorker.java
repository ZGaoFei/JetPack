package com.example.jetpack.workmanager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class TestWorker extends Worker {
    private Context context;

    public TestWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("zgf", "test worker do something!!");
//        Toast.makeText(context, "test worker do something!!!", Toast.LENGTH_SHORT).show();

        return Result.success();
    }
}
