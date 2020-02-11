package com.qdhc.ny.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Process;
import android.support.multidex.MultiDexApplication;

import com.qdhc.ny.BuildConfig;
import com.qdhc.ny.bmob.UserInfo;
import com.qdhc.ny.common.Constant;
import com.sj.core.app.ProjectInit;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.Bmob;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;


/**
 * Created by 申健 on 2018/8/11.
 */

public class BaseApplication extends MultiDexApplication {
    public static Typeface iconfont;

    public static Map<String, UserInfo> userInfoMap = new HashMap<>();

    public static Typeface getIconfont(Context context) {
        if (iconfont != null) {
            return iconfont;
        } else {
            iconfont = Typeface.createFromAsset(context.getAssets(), "iconfont.ttf");
        }
        return iconfont;
    }

    private int count = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        ProjectInit.init(this)
                //必须以 /结束
                .withApiHost(Constant.SYS_ROOT).configure();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //initUmeng();
                    //设置线程的优先级，不与主线程抢资源
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                    //子线程初始化第三方组件
                    Thread.sleep(5000);//建议延迟初始化，可以发现是否影响其它功能，或者是崩溃！

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }).start();

        Bmob.initialize(this, "5f221eb7f4e0909d78eb5650d207fb86");

        // 腾讯bugly初始化
        CrashReport.initCrashReport(getApplicationContext(), "acadddbef3", BuildConfig.DEBUG);

        HeConfig.init("HE1603110949591229", "e61e27000f6d422bbb0f2974957819a2");
        HeConfig.switchToFreeServerNode();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                count++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                if (count > 0) {
                    count--;
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    /**
     * 判断app是否在后台
     *
     * @return
     */
    public boolean isBackground() {
        if (count <= 0) {
            return true;
        } else {
            return false;
        }
    }

}
