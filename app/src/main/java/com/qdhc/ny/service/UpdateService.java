package com.qdhc.ny.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;

import java.io.File;

/**
 * 已弃用
 * 版本更新的服务
 * Created by Mr_zhou on 2017/8/14.
 */

public class UpdateService extends Service {

    private final static String TAG = UpdateService.class.getSimpleName();

    public UpdateService() {

    }

    /**
     * 安卓系统下载类
     **/
    DownloadManager manager;

    /**
     * 接收下载完的广播
     **/
    DownloadCompleteReceiver receiver;

    /**
     * 初始化下载器
     **/
    private void initDownManager(String urlPath) {
        if (TextUtils.isEmpty(urlPath))
            return;
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();

        //设置下载地址
        Uri parse = Uri.parse(urlPath);
        DownloadManager.Request down = new DownloadManager.Request(parse);

        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

        // 下载时，通知栏显示途中
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        }

        // 显示下载界面
        down.setVisibleInDownloadsUi(true);

        // 设置下载后文件存放的位置
        String apkName = parse.getLastPathSegment();

        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String downPath = "Nongyeju/download";
        File downDir = new File(sdPath + "/" + downPath);
        if (!downDir.exists()) {
            downDir.mkdirs();
        }

        down.setDestinationInExternalPublicDir(downPath, apkName);

        // 将下载请求放入队列
        manager.enqueue(down);

        //注册下载广播
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            initDownManager(intent.getStringExtra("url"));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        // 注销下载广播
        if (receiver != null)
            unregisterReceiver(receiver);
        super.onDestroy();
    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            //判断是否下载完成的广播
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {

                //获取下载的文件id
                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                //自动安装apk
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    Uri uriForDownloadedFile = manager.getUriForDownloadedFile(downId);
                    installApkNew(uriForDownloadedFile);
                }
                //停止服务并关闭广播
                UpdateService.this.stopSelf();
            }
        }

        //安装apk
        protected void installApkNew(Uri uri) {
            //执行动作
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //执行的数据类型
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判读版本是否在7.0以上
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            }
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            startActivity(intent);

        }
    }

}