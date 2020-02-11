package com.sj.core.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by 申健 on 2018/8/12.
 */

public class BaseUtil {

    /**
     * 转换时间InMillis
     *
     * @param time 时间字符串
     * @return long(如果时间字符串无法转换返回0)
     */
    public static long timeInMillis(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        Date date = null;
        try {
            date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取当前版本号(注意此处获取的为3位服务器版本号)
     *
     * @param context
     * @return 当前版本号
     */
    public static final String getAppVersionForSever(Context context) {
        String version = null;
        try {
            version = getAppVersionName(context);
            String[] vs = version.split("\\.");
            if (vs.length >= 4) {
                version = vs[0] + "." + vs[1] + "." + vs[2];
            }
        } catch (PackageManager.NameNotFoundException e) {
            version = "1.0.0";
        }
        return version;
    }

    public static final String getAppVersionName(Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
        return pi.versionName;
    }

    /**
     * 获取当前版本号(注意此处获取的为当前4位版本号的全部字符串)
     *
     * @param context
     * @return 当前版本号
     */
    public static final String getAppVersionForTest(Context context) {
        String version = null;
        try {
            version = getAppVersionName(context);
        } catch (PackageManager.NameNotFoundException e) {
            version = "1.0.0";
        }
        return version;
    }

    /***
     * 设置某一段文字颜色
     * @param src
     * @param color
     * @return
     */
    public static String colorFont(String src, String color) {
        StringBuffer strBuf = new StringBuffer();

        strBuf.append("<font color=").append(color).append(">").append(src)
                .append("</font>");
        return strBuf.toString();
    }

    /**
     * 比较app版本是否需要升级
     *
     * @param current
     * @param service
     * @return
     */
    public static boolean isNeedUpDate(String current, String service) {
        if (isNull(current) || isNull(service))
            return false;

        String[] c = current.split("\\."); // 2.2.3
        String[] s = service.split("\\."); // 2.4.0
        long fc = Long.valueOf(c[0]); // 2
        long fs = Long.valueOf(s[0]); // 2
        if (fc > fs)
            return false;
        else if (fc < fs) {
            return true;
        } else {
            long sc = Long.valueOf(c[1]); // 2
            long ss = Long.valueOf(s[1]); // 4
            if (sc > ss)
                return false;
            else if (sc < ss) {
                return true;
            } else {
                long tc = Long.valueOf(c[2]); // 3
                long ts = Long.valueOf(s[2]); // 0
                if (tc >= ts)
                    return false;
                else
                    return true;
            }
        }
    }

    public static final boolean isNull(String str) {
        return str == null || "".equals(str.trim());
    }


    /**
     * 隐藏手机号和邮箱显示
     *
     * @param old     需要隐藏的手机号或邮箱
     * @param keytype 1手机2邮箱
     * @return
     */
    public static String hide(String old, String keytype) {
        try {
            if ("1".equals(keytype))
                return old.substring(0, 3) + "****" + old.substring(7, 11);
            else {
                StringBuilder sb = new StringBuilder();
                String[] s = old.split("@");
                int l = s[0].length();
                int z = l / 3;
                sb.append(s[0].substring(0, z));
                int y = l % 3;
                for (int i = 0; i < z + y; i++)
                    sb.append("*");
                sb.append(s[0].substring(z * 2 + y, l));
                sb.append("@");
                if (s[1] == null) {

                }
                sb.append(s[1]);
                return sb.toString();
            }
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public static boolean isAppOnForeground(Context context) {
        // Returns a list of application processes that are running on the
        // device
        ActivityManager activityManager = (ActivityManager) context
                .getApplicationContext().getSystemService(
                        Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();
        System.out.println("packageName=" + packageName);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断当前用户是否是第三方登录
     *
     * @param context
     * @return
     */
    public static boolean isThirdSave(Context context) {
        String thirdsave = SharedPreferencesUtil.get(context, "thirdsave");
        return "true".equals(thirdsave);
    }

    /**
     * 设置当前用户是否是第三方登录
     *
     * @param context
     * @param thirdsave
     */
    public static void setThirdSave(Context context, boolean thirdsave) {
        SharedPreferencesUtil.save(context, "thirdsave", thirdsave ? "true"
                : "false");
    }

    public static final int getAppVersionCode(Context context) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
        return pi.versionCode;
    }


}
