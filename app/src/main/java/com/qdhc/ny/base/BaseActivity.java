package com.qdhc.ny.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.qdhc.ny.R;
import com.sj.core.utils.AcitityManagerUtil;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by 申健 on 2018/8/12.
 */

public abstract class BaseActivity extends FragmentActivity {
    protected Activity mContext;
    protected ZLoadingDialog mDialog;

    protected Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setColor(mContext, getResources().getColor(R.color.themecolor));
        //设置布局
        setContentView(intiLayout());
        initView();
        initClick();
        initData();
        EventBus.getDefault().register(this);
        AcitityManagerUtil.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     *
     */
    @Subscribe
    public void onEvent(String event) {
    }

    /**
     * 设置布局
     *
     * @return
     */
    protected abstract int intiLayout();

    protected abstract void initView();

    protected abstract void initClick();

    protected abstract void initData();


    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    public void setColor(Activity activity, int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {// 5.0的全透明设置
            Window window = activity.getWindow();
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(color);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            ViewGroup mContentView = (ViewGroup) activity
                    .findViewById(Window.ID_ANDROID_CONTENT);

            // First translucent status bar.
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight();

            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView
                        .getLayoutParams();
                // 如果已经为 ChildView 设置过了 marginTop, 再次调用时直接跳过
                if (lp != null && lp.topMargin < statusBarHeight
                        && lp.height != statusBarHeight) {
                    // 不预留系统空间
                    mChildView.setFitsSystemWindows(false);
//					ViewCompat.setFitsSystemWindows(mChildView, false);
                    lp.topMargin += statusBarHeight;
                    mChildView.setLayoutParams(lp);
                }
            }

            View statusBarView = mContentView.getChildAt(0);
            if (statusBarView != null
                    && statusBarView.getLayoutParams() != null
                    && statusBarView.getLayoutParams().height == statusBarHeight) {
                // 避免重复调用时多次添加 View
                statusBarView.setBackgroundColor(color);
                return;
            }
            statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
            statusBarView.setBackgroundColor(color);
            // 向 ContentView 中添加假 View
            mContentView.addView(statusBarView, 0, lp);
        }
    }

    public int getStatusBarHeight() {
        // 获得状态栏高度
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = mContext.getResources().getDimensionPixelSize(resourceId);
        return statusBarHeight;

    }

    /**
     * 显示对话框
     */
    protected void showDialog(String text) {
        if (mDialog == null) {
            mDialog = new ZLoadingDialog(this);
            mDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)//设置类型
                    .setLoadingColor(Color.parseColor("#4DC06B"))//颜色
                    .setHintTextSize(16) // 设置字体大小 dp
                    .setHintTextColor(Color.parseColor("#4DC06B"))  // 设置字体颜色
                    .setDurationTime(0.8) // 设置动画时间百分比 - 0.5倍
                    .setDialogBackgroundColor(Color.parseColor("#FFFFFF")) // 设置背景色，默认白色
                    .setCancelable(false);
        }
        mDialog.setHintText(text);
        mDialog.show();
    }

    /**
     * 对话框消失
     */
    protected void dismissDialog() {
        if (mDialog != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDialog.dismiss();
                }
            }, 1500);
        }
    }

    /**
     * 对话框立即消失
     */
    protected void dismissDialogNow() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

}
