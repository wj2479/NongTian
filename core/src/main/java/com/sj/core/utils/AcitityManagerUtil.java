package com.sj.core.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;

import java.util.Stack;

/**
 * acitivity管理类
 * Created by 申健 on 2018/5/17.
 */

public class AcitityManagerUtil {
    private static Stack<Activity> activityStack;
    private static AcitityManagerUtil instance;
    private PendingIntent restartIntent;

    private AcitityManagerUtil() {
    }

    /**
     * 单一实例
     */
    public static AcitityManagerUtil getInstance() {
        if (instance == null) {
            instance = new AcitityManagerUtil();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (activityStack != null) {
            while (activityStack.iterator().hasNext()){
                Activity activity=activityStack.iterator().next();
                if (activity.getClass().equals(cls)) {
                    activity.finish();
                    activityStack.pop();
                }
            }

        }

    }

    /**
     * 退出应用程序
     */
    public void exitApp(Context context) {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
}
