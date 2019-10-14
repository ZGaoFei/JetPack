package com.example.jetpack.downloadmanager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * 单任务下载
 */
public class DownloadManagerUtil {
    private Context context;
    private String url;
    private String name;

    private DownloadStateListener listener;

    private String path;
    private long downloadId;
    private DownloadManager downloadManager;

    public DownloadManagerUtil(Context context, String url) {
        this(context, url, getFileNameByUrl(url));
    }

    public DownloadManagerUtil(Context context, String url, String name) {
        this.context = context;
        this.url = url;
        this.name = name;
    }

    public DownloadManagerUtil setListener(DownloadStateListener listener) {
        this.listener = listener;
        return this;
    }

    public void download() {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedOverRoaming(false);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle(name);
        request.setDescription("正在下载中……");
//        request.setVisibleInDownloadsUi(true);

        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), name);
        request.setDestinationUri(Uri.fromFile(file));
        path = file.getAbsolutePath();

        if (downloadManager == null) {
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        }

        if (downloadManager != null) {
            if (listener != null) {
                listener.onPrepare();
            }
            downloadId = downloadManager.enqueue(request);
        }

        context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void cancel() {
        downloadManager.remove(downloadId);
        context.unregisterReceiver(receiver);
    }

    private static String getFileNameByUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        String filename = url.substring(url.lastIndexOf("/") + 1);
        filename = filename.substring(0, filename.indexOf("?") == -1 ? filename.length() : filename.indexOf("?"));
        return filename;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            Cursor cursor = downloadManager.query(query);
            if (cursor.moveToFirst()) {
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                switch (status) {
                    case DownloadManager.STATUS_PAUSED:
                        break;
                    case DownloadManager.STATUS_PENDING:
                        break;
                    case DownloadManager.STATUS_RUNNING:
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        if (listener != null) {
                            listener.onSuccess(path);
                        }
                        cursor.close();
                        context.unregisterReceiver(receiver);
                        break;
                    case DownloadManager.STATUS_FAILED:
                        if (listener != null) {
                            listener.onFailed(new Throwable("下载失败！"));
                        }
                        cursor.close();
                        context.unregisterReceiver(receiver);
                        break;
                }
            }
        }
    };

    public interface DownloadStateListener {
        void onPrepare();

        void onSuccess(String path);

        void onFailed(Throwable throwable);
    }
}
