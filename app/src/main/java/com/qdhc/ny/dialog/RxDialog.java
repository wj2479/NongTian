package com.qdhc.ny.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by 申健 on 2019/5/16.
 */

public class RxDialog  extends Dialog {
    protected Context mContext;
    protected WindowManager.LayoutParams mLayoutParams;

    public WindowManager.LayoutParams getLayoutParams() {
        return this.mLayoutParams;
    }

    public RxDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.initView(context);
    }

    public RxDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.initView(context);
    }

    public RxDialog(Context context) {
        super(context);
        this.initView(context);
    }

    private void initView(Context context) {
        this.requestWindowFeature(1);
        this.getWindow().setBackgroundDrawableResource(com.vondear.rxui.R.drawable.transparent_bg);
        this.mContext = context;
        Window window = this.getWindow();
        this.mLayoutParams = window.getAttributes();
        this.mLayoutParams.alpha = 1.0F;
        window.setAttributes(this.mLayoutParams);
        if(this.mLayoutParams != null) {
            this.mLayoutParams.height = -1;
            this.mLayoutParams.gravity = 17;
        }

    }

    public RxDialog(Context context, float alpha, int gravity) {
        super(context);
        this.requestWindowFeature(1);
        this.getWindow().setBackgroundDrawableResource(com.vondear.rxui.R.drawable.transparent_bg);
        this.mContext = context;
        Window window = this.getWindow();
        this.mLayoutParams = window.getAttributes();
        this.mLayoutParams.alpha = 1.0F;
        window.setAttributes(this.mLayoutParams);
        if(this.mLayoutParams != null) {
            this.mLayoutParams.height = -1;
            this.mLayoutParams.gravity = gravity;
        }

    }

    public void skipTools() {
        if(Build.VERSION.SDK_INT >= 19) {
            this.getWindow().setFlags(1024, 1024);
        }
    }

    public void setFullScreen() {
        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = -1;
        lp.height = -1;
        window.setAttributes(lp);
    }

    public void setFullScreenWidth() {
        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = -1;
        lp.height = -2;
        window.setAttributes(lp);
    }

    public void setFullScreenHeight() {
        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = -2;
        lp.height = -1;
        window.setAttributes(lp);
    }

    public void setOnWhole() {

//只有这样才能弹框
        if (Build.VERSION.SDK_INT>=26) {//8.0新特性
            this.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        }else{
            this.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
    }
}
