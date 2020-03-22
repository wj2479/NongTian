package com.qdhc.ny.service;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.google.gson.Gson;
import com.qdhc.ny.entity.AppUpdate;
import com.qdhc.ny.utils.SystemUtils;
import com.sj.core.net.Rx.RxRestClient;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 版本更新管理类
 */

public class UpadateManager {

    public static void checkVersion(final Context ctx) {
        HashMap<String, Object> params = new HashMap<>();

        RxRestClient.create()
                .url("app/getUpdateInfo")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("TAG", "获取版本更新信息:" + s);
                        JSONObject jsonObject = new JSONObject(s);
                        if (jsonObject.getInt("code") == 1000) {
                            JSONObject result = jsonObject.getJSONObject("result");
                            AppUpdate data = new Gson().fromJson(result.toString(), AppUpdate.class);
                            // 登录成功
                            if (data.getVersionCode() >= SystemUtils.getAppVersionCode(ctx)) {
                                initDialog(ctx, data);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("TAG", "获取版本更新出错:" + throwable.toString());
                    }
                });
    }

    /**
     * 显示对话框
     *
     * @param context
     * @param appUpdate
     */
    private static void initDialog(final Context context, final AppUpdate appUpdate) {
        final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(context);

        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        sb.append("新版本: " + appUpdate.getVersionName());
        sb.append("</p>");

//        try {
//            URL url = new URL(appUpdate.getDownloadFile().getUrl());
//            HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
//            int fileLength = urlcon.getContentLength();

//            sb.append("大小:" + FormetFileSize(15456452));
//        } catch (MalformedURLException e) {
//        } catch (IOException e) {
//        }
        sb.append("<p>");

        sb.append("更新内容:<br><br>" + appUpdate.getContent());
        sb.append("</p>");

        Spanned text = Html.fromHtml(sb.toString());
        rxDialogSureCancel.getContentView().setText(text);
        rxDialogSureCancel.getContentView().setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);
        rxDialogSureCancel.getContentView().setGravity(Gravity.LEFT);
        rxDialogSureCancel.getTitleView().setText("版本更新提示");
        rxDialogSureCancel.getTitleView().setTextSize(16.0f);
        rxDialogSureCancel.setSure("立即更新");
        rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = appUpdate.getUrl();
                Log.e("TAG", "新版本下载地址:" + url);
                Intent intent = new Intent(context, UpdateService.class);
                intent.putExtra("url", url);
                context.startService(intent);
                rxDialogSureCancel.cancel();
            }
        });
        rxDialogSureCancel.setCancel("下次再说");
        rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogSureCancel.cancel();
            }
        });
        rxDialogSureCancel.show();
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }
}
