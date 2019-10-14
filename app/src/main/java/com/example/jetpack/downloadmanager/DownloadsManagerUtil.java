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
 * 多任务下载
 */
public class DownloadsManagerUtil {

    private Context context;
    private String[] urls;
    private String[] names;

    private DownloadStateListener listener;

    private String[] paths;
    private DownloadModel[] downloadModels;
    private DownloadManager downloadManager;

    public DownloadsManagerUtil(Context context, String... urls) {
        this.context = context;
        this.urls = urls;

        names = new String[urls.length];
        paths = new String[urls.length];
        downloadModels = new DownloadModel[urls.length];
    }

//    public DownloadsManagerUtil(Context context, String... url, String... name) {
//        this.context = context;
//        this.urls = url;
//        this.names = name;
//    }

    public DownloadsManagerUtil setListener(DownloadStateListener listener) {
        this.listener = listener;
        return this;
    }

    public void download() {
        if (urls == null) {
            if (listener != null) {
                listener.onFailed(new Throwable("没有下载任务！！！"), -1);
            }
            return;
        }

        if (downloadManager == null) {
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        }
        if (listener != null) {
            listener.onPrepare();
        }
        for (int i = 0; i < urls.length; i++) {
            String url = urls[i];
            if (!TextUtils.isEmpty(url)) {
                String name = getFileNameByUrl(url);
                names[i] = name;
                DownloadManager.Request request = getRequest(url, name, i);

                if (downloadManager != null) {
                    long downloadId = downloadManager.enqueue(request);
                    downloadModels[i] = new DownloadModel(downloadId, -1);
                }
            }
        }

        context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private DownloadManager.Request getRequest(String url, String name, int i) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedOverRoaming(false);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle(name);
        request.setDescription("正在下载中……");
//        request.setVisibleInDownloadsUi(true);

        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), name);
        paths[i] = file.getAbsolutePath();
        request.setDestinationUri(Uri.fromFile(file));

        return request;
    }

    public void cancel() {
        for (int i = 0; i < downloadModels.length; i++) {
            downloadManager.remove(downloadModels[i].downloadId);
        }
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

            for (int i = 0; i < downloadModels.length; i++) {
                DownloadModel downloadModel = downloadModels[i];
                long downloadId = downloadModel.downloadId;
                int downloadStatus = downloadModel.state;
                if (downloadStatus != DownloadManager.STATUS_SUCCESSFUL && downloadStatus != DownloadManager.STATUS_FAILED) {
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
                                downloadModel.setState(DownloadManager.STATUS_SUCCESSFUL);
                                if (listener != null) {
                                    listener.onSuccess(paths[i], i);
                                }
                                cursor.close();

                                unregister();
                                break;
                            case DownloadManager.STATUS_FAILED:
                                downloadModel.setState(DownloadManager.STATUS_FAILED);
                                if (listener != null) {
                                    listener.onFailed(new Throwable("下载失败！"), i);
                                }
                                cursor.close();

                                unregister();
                                break;
                        }
                    }
                }
            }
        }
    };

    private void unregister() {
        boolean isUnRegister = true;
        for (int i = 0; i < downloadModels.length; i++) {
            int downloadStatus = downloadModels[i].state;
            if (downloadStatus != DownloadManager.STATUS_SUCCESSFUL && downloadStatus != DownloadManager.STATUS_FAILED) {
                isUnRegister = false;
                break;
            }
        }

        if (isUnRegister) {
            context.unregisterReceiver(receiver);
        }
    }

    public interface DownloadStateListener {
        void onPrepare();

        void onSuccess(String path, int index);

        void onFailed(Throwable throwable, int index);
    }

    public static class DownloadModel {
        private long downloadId;
        private int state;

        public DownloadModel() {
        }

        public DownloadModel(long downloadId, int state) {
            this.downloadId = downloadId;
            this.state = state;
        }

        public long getDownloadId() {
            return downloadId;
        }

        public void setDownloadId(long downloadId) {
            this.downloadId = downloadId;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
