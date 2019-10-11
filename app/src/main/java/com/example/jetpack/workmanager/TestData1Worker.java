package com.example.jetpack.workmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class TestData1Worker extends Worker {

    public TestData1Worker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork() {
        int a = getInputData().getInt("result", 0);
        int result = a * a;

        Data data = new Data.Builder()
                .putInt("result1", result)
                .build();

        return Result.success(data);
    }
}
