package com.example.jetpack.workmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class TestDataWorker extends Worker {

    public TestDataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork() {
        int a = getInputData().getInt("a", 0);
        int b = getInputData().getInt("b", 0);
        int sum = a + b;

        Data data = new Data.Builder()
                .putInt("result", sum)
                .build();

        return Result.success(data);
    }
}
