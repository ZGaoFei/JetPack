package com.example.jetpack.downloadmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.jetpack.R;

public class DownloadActivity extends AppCompatActivity {

    private static final String URL = "http://app.autohome.com.cn/download/autohome.apk";
    private static final String URL1 = "http://xunlei.xiazai-zuida.com/1910/海贼王-906.mp4";
    private static final String URL2 = "http://e.hiphotos.baidu.com/nuomi/pic/item/a044ad345982b2b74ba5c21a37adcbef76099b32.jpg";
    private static final String URL3 = "http://img5.imgtn.bdimg.com/it/u=3387958471,2217499148&fm=26&gp=0.jpg";
    private static final String URL4 = "http://img3.imgtn.bdimg.com/it/u=4186486800,813755701&fm=26&gp=0.jpg";

    private DownloadManagerUtil managerUtil;
    private DownloadsManagerUtil util;

    public static void start(Context context) {
        context.startActivity(new Intent(context, DownloadActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        TextView textView = findViewById(R.id.tv_start_download);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });

        TextView tvCancel = findViewById(R.id.tv_cancel_download);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managerUtil.cancel();
            }
        });

        TextView tvStart = findViewById(R.id.tv_start_downloads);
        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloads();
            }
        });

        TextView tvCancelAll = findViewById(R.id.tv_cancel_downloads);
        tvCancelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                util.cancel();
            }
        });
    }

    private void download() {
        managerUtil = new DownloadManagerUtil(this, URL2).setListener(new DownloadManagerUtil.DownloadStateListener() {
            @Override
            public void onPrepare() {
                Log.e("zgf", "========onPrepare=========");
            }

            @Override
            public void onSuccess(String path) {
                Log.e("zgf", "========onSuccess=========" + path);
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.e("zgf", "========onFailed=========" + throwable.getMessage());
            }
        });
        managerUtil.download();
    }

    private void downloads() {
        util = new DownloadsManagerUtil(this, URL1, URL2, URL3, URL4).setListener(new DownloadsManagerUtil.DownloadStateListener() {
            @Override
            public void onPrepare() {
                Log.e("zgf", "========onPrepare=========");
            }

            @Override
            public void onSuccess(String path, int index) {
                Log.e("zgf", "========onSuccess=========" + path + "====" + index);
            }

            @Override
            public void onFailed(Throwable throwable, int index) {
                Log.e("zgf", "========onFailed=========" + throwable.getMessage() + "====" + index);
            }
        });
        util.download();
    }
}
