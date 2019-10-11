package com.example.jetpack.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jetpack.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WorkManagerActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvStart;
    private TextView tvStop;

    private Context context;

    public static void start(Context context) {
        context.startActivity(new Intent(context, WorkManagerActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager);

        context = this;

        tvStart = findViewById(R.id.tv_start);
        tvStop = findViewById(R.id.tv_stop);
        tvStart.setOnClickListener(this);
        tvStop.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                Toast.makeText(context, "click start", Toast.LENGTH_SHORT).show();

//                initWork();
//                initWork1();
//                addConstraint();
//                cancelWork();

//                initPeriodicWork();

//                doMultiWork();
//                doMultiWorkObserve();
//                doMultiWork1();

//                combine();

//                beginUniqueWork();

//                setDataWork();
                lineWork();
                break;
            case R.id.tv_stop:
                break;
        }
    }

    /**
     * 初始化
     */
    private void initWork() {
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(TestWorker.class).build();

        WorkManager.getInstance(this).enqueue(request);
    }

    /**
     * 添加任务执行监听
     */
    private void initWork1() {
        OneTimeWorkRequest request = new OneTimeWorkRequest
                .Builder(TestWorker.class)
                .addTag("one")
                .build();

        WorkManager manager = WorkManager.getInstance(this);
        manager.enqueue(request);

        manager.getWorkInfosByTagLiveData("one").observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                for (WorkInfo info : workInfos) {
                    Log.e("zgf", "=======" + info.getState().name());
                }
            }
        });

        manager.getWorkInfoByIdLiveData(request.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                Log.e("zgf", "=================" + workInfo.getState().name());
            }
        });
    }

    /**
     * 添加约束
     * setRequiresDeviceIdle(boolean) :设备空闲
     * setRequiredNetworkType(NetworkType) :网络状态
     * setRequiresBatteryNotLow(boolean) :电池电量低
     * setRequiresStorageNotLow(boolean) :设备可用存储是否不低于临界阈值
     * setRequiresCharging(boolean) :是否要插入设备（接入电源）
     */
    private void addConstraint() {
        Constraints constraints = new Constraints
                .Builder()
//                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .setRequiresBatteryNotLow(true)
                .setRequiresCharging(true)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest
                .Builder(TestWorker.class)
                .addTag("one")
                .setConstraints(constraints)
                .build();

        WorkManager manager = WorkManager.getInstance(this);
        manager.enqueue(request);
    }

    /**
     * 取消任务
     */
    private void cancelWork() {
        Constraints constraints = new Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest
                .Builder(TestWorker.class)
                .addTag("one")
                .setConstraints(constraints)
                .build();

        WorkManager manager = WorkManager.getInstance(this);
        manager.enqueue(request);

        manager.getWorkInfoByIdLiveData(request.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                Log.e("zgf", "=================" + workInfo.getState().name());
            }
        });

//        manager.cancelAllWork();
//        manager.cancelAllWorkByTag("one");
        tvStart.postDelayed(new Runnable() {
            @Override
            public void run() {
                manager.cancelWorkById(request.getId());
            }
        }, 5000);
    }

    /**
     * 重复执行一个任务
     */
    private void initPeriodicWork() {
        PeriodicWorkRequest request = new PeriodicWorkRequest
                .Builder(TestWorker.class, 5, TimeUnit.SECONDS)
                .build();

        WorkManager manager = WorkManager.getInstance(this);
        manager.enqueue(request);

        manager.getWorkInfoByIdLiveData(request.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                Log.e("zgf", "=================" + workInfo.getState().name());
            }
        });
    }

    /**
     * 执行多个任务
     */
    private void doMultiWork() {
        OneTimeWorkRequest request = new OneTimeWorkRequest
                .Builder(TestWorker.class)
                .addTag("one")
                .build();

        OneTimeWorkRequest request1 = new OneTimeWorkRequest
                .Builder(Test1Worker.class)
                .addTag("two")
                .build();

        OneTimeWorkRequest request2 = new OneTimeWorkRequest
                .Builder(Test2Worker.class)
                .addTag("three")
                .build();

        WorkManager manager = WorkManager.getInstance(this);
        manager.beginWith(request1).then(request).then(request2).enqueue();

        manager.getWorkInfoByIdLiveData(request.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                Log.e("zgf", "=================" + workInfo.getState().name());
            }
        });
    }

    private void doMultiWorkObserve() {
        OneTimeWorkRequest request = new OneTimeWorkRequest
                .Builder(TestWorker.class)
                .addTag("one")
                .build();

        OneTimeWorkRequest request1 = new OneTimeWorkRequest
                .Builder(Test1Worker.class)
                .addTag("one")
                .build();

        OneTimeWorkRequest request2 = new OneTimeWorkRequest
                .Builder(Test2Worker.class)
                .addTag("one")
                .build();

        WorkManager manager = WorkManager.getInstance(this);
        manager.beginWith(request1).then(request).then(request2).enqueue();

        manager.getWorkInfosByTagLiveData("one").observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                for (WorkInfo info : workInfos) {
                    if (info.getId().equals(request.getId())) {
                        Log.e("zgf", "========request=======" + info.getState().name());
                    } else if(info.getId().equals(request1.getId())) {
                        Log.e("zgf", "======request1=====" + info.getState().name());
                    } else {
                        Log.e("zgf", "====request2===" + info.getState().name());
                    }
                }
            }
        });
    }

    /**
     * 并行执行多个任务
     *
     * 并行执行多个任务时，无法再执行多个任务里面的任务，即任务只能执行一次
     * 除非重新build此任务
     *
     */
    private void doMultiWork1() {
        OneTimeWorkRequest request = new OneTimeWorkRequest
                .Builder(TestWorker.class)
                .addTag("one")
                .build();

        OneTimeWorkRequest request1 = new OneTimeWorkRequest
                .Builder(Test1Worker.class)
                .addTag("two")
                .build();

        OneTimeWorkRequest request2 = new OneTimeWorkRequest
                .Builder(Test2Worker.class)
                .addTag("three")
                .build();

        OneTimeWorkRequest request4 = new OneTimeWorkRequest
                .Builder(TestWorker.class)
                .addTag("one")
                .build();

        List<OneTimeWorkRequest> multi = new ArrayList<>();
        multi.add(request2);
        multi.add(request1);

        WorkManager.getInstance(this)
                .beginWith(request)
//                .then(request1)
//                .then(request2)
                .then(multi)
                .then(request4)
                .enqueue();
    }

    /**
     * 复杂的链式任务
     *
     * 重复的任务只会执行一次
     */
    private void combine() {
        OneTimeWorkRequest request = new OneTimeWorkRequest
                .Builder(TestWorker.class)
                .addTag("one")
                .build();

        OneTimeWorkRequest request1 = new OneTimeWorkRequest
                .Builder(Test1Worker.class)
                .addTag("two")
                .build();

        OneTimeWorkRequest request2 = new OneTimeWorkRequest
                .Builder(Test2Worker.class)
                .addTag("three")
                .build();

        OneTimeWorkRequest request3 = new OneTimeWorkRequest
                .Builder(TestWorker.class)
                .addTag("one")
                .build();

        WorkContinuation a = WorkManager.getInstance(this).beginWith(request).then(request1);
        WorkContinuation b = WorkManager.getInstance(this).beginWith(request).then(request2);

        List<WorkContinuation> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        WorkContinuation
                .combine(list)
                .then(request3)
//                .then(request3)
                .enqueue();

    }

    /**
     * ExistingWorkPolicy.APPEND:将新序列附加到现有序列，在现有序列的最后一个任务完成后运行新序列的第一个任务
     * ExistingWorkPolicy.KEEP:保留现有序列并忽略您的新请求
     * ExistingWorkPolicy.REPLACE:取消现有序列并将其替换为新序列
     *
     * 创建唯一的工作序列
     */
    private void beginUniqueWork() {
        OneTimeWorkRequest request = new OneTimeWorkRequest
                .Builder(TestWorker.class)
                .addTag("one")
                .build();

        OneTimeWorkRequest request1 = new OneTimeWorkRequest
                .Builder(Test1Worker.class)
                .addTag("two")
                .build();

        WorkManager.getInstance(this).beginUniqueWork("worker", ExistingWorkPolicy.REPLACE, request).enqueue();
        WorkManager.getInstance(this).beginUniqueWork("worker", ExistingWorkPolicy.REPLACE, request1).enqueue();
    }

    /**
     * 传递参数和接收参数
     */
    private void setDataWork() {
        Data data = new Data.Builder()
                .putInt("a", 10)
                .putInt("b", 20)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest
                .Builder(TestDataWorker.class)
                .addTag("one")
                .setInputData(data)
                .build();

        WorkManager manager = WorkManager.getInstance(this);
        manager.enqueue(request);

        manager.getWorkInfoByIdLiveData(request.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                    Data outputData = workInfo.getOutputData();
                    Log.e("zgf", "======result=========" + outputData.getInt("result", 0));
                }
            }
        });
    }

    /**
     * 将数据传给worker1处理加操作，
     * 将worker1处理的结果传给worker2处理平方操作
     * 最后返回结果
     *
     * 链式传值操作
     */
    private void lineWork() {
        Data data = new Data.Builder()
                .putInt("a", 10)
                .putInt("b", 20)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest
                .Builder(TestDataWorker.class)
                .addTag("one")
                .setInputData(data)
                .build();

        OneTimeWorkRequest request2 = new OneTimeWorkRequest
                .Builder(TestData1Worker.class)
                .addTag("two")
                .setInputData(data)
                .build();

        WorkManager manager = WorkManager.getInstance(this);
        manager.beginWith(request).then(request2).enqueue();

        manager.getWorkInfoByIdLiveData(request2.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                    Data outputData = workInfo.getOutputData();
                    Log.e("zgf", "======result=========" + outputData.getInt("result1", 0));
                }
            }
        });
    }
}
