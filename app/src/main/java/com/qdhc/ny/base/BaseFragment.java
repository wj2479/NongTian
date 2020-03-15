package com.qdhc.ny.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.qdhc.ny.R;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by 申健 on 2018/8/19.
 */

public abstract class BaseFragment extends Fragment {
    Activity mContext;

    protected ZLoadingDialog mDialog;

    protected Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(intiLayout(), null);
        isViewPrepare = true;
        mContext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        lazyLoadDataIfPrepared();
        initClick();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

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
     * 视图是否加载完毕
     */
    private boolean isViewPrepare = false;
    /**
     * 数据是否加载过了
     */
    private boolean hasLoadData = false;

    public boolean isViewPrepare() {
        return isViewPrepare;
    }

    public void setViewPrepare(boolean viewPrepare) {
        isViewPrepare = viewPrepare;
    }

    public boolean isHasLoadData() {
        return hasLoadData;
    }

    public void setHasLoadData(boolean hasLoadData) {
        this.hasLoadData = hasLoadData;
    }

    /**
     * 懒加载
     */
    protected abstract void lazyLoad();

    private void lazyLoadDataIfPrepared() {
        if (getUserVisibleHint() && isViewPrepare && !hasLoadData) {
            lazyLoad();
            hasLoadData = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared();
        }
    }


    /**
     * 显示对话框
     */
    protected void showDialog(String text) {
        if (mDialog == null) {
            mDialog = new ZLoadingDialog(getContext());
            mDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)//设置类型
                    .setLoadingColor(getResources().getColor(R.color.themecolor))//颜色
                    .setHintTextSize(16) // 设置字体大小 dp
                    .setHintTextColor(getResources().getColor(R.color.themecolor))  // 设置字体颜色
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
