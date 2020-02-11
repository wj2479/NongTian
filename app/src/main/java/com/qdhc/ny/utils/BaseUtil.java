package com.qdhc.ny.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.sj.core.utils.SharedPreferencesUtil;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static android.text.TextUtils.isEmpty;


/**
 * 工具类
 */
public class BaseUtil {
    public static int screenWidth;       // 屏幕宽（像素，如：480px）
    public static int screenHeight;      // 屏幕高（像素，如：800p）
    public static String MobileRegular = "^1[3|4|5|6|7|8|9][0-9]\\d{8}$";//
    public static String RegExp15 = "\\d{14}([0-9]|X|x)";//[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$
    public static String RegExp18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";
    private static double EARTH_RADIUS = 6378.137;// 地球半径
    private static long lastClickTime;

    private static String[] charArray = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "q", "w", "e", "r", "t", "y", "u", "i", "o", "p",
            "a", "s", "d", "f", "g", "h", "j", "k", "l",
            "z", "x", "c", "v", "b", "n", "m"};

    /**
     * 获取随机的字符串+数字
     *
     * @param length
     * @return
     */
    public static String getRandomStr(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        while (sb.length() < length) {
            sb.append(charArray[random.nextInt(charArray.length)]);
        }
        return sb.toString();
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String transDuration(long duration) {
        String ds = "";
        long min = duration / 60;
        if (min < 60) {
            ds += (min + "分钟");
        } else {
            long hour = min / 60;
            long rm = min % 60;
            if (rm > 0)
                ds += (hour + "小时" + rm + "分钟");
            else
                ds += (hour + "小时");
        }
        return ds;
    }

    /**
     * 格式化身份证号
     * 返回
     *
     * @return String
     */
    public static String formatIdCard(String id) {
        if (id != null && id.length() == 18) {
            String rep = id.substring(6, 16);
            String str1 = id.substring(0, 6);
            String str2 = id.substring(16, 18);

            id = str1 + "**********" + str2;
        } else if (id != null && id.length() == 15) {
            String rep = id.substring(6, 13);
            String str1 = id.substring(0, 6);
            String str2 = id.substring(13, 15);

            id = str1 + "*******" + str2;
        }


        return id;
    }

    /**
     * 格式化手机号
     * 返回
     *
     * @return String
     */
    public static String formatMobileRegister(String mobile) {
        if (mobile != null && mobile.length() == 11) {
            String rep = mobile.substring(3, 7);
            String str1 = mobile.substring(0, 3);
            String str2 = mobile.substring(7, 11);

            mobile = str1 + "****" + str2;
        }
        return mobile;
    }

    /**
     * 转换时间显示形式(与当前系统时间比较),在显示即时聊天的时间时使用
     *
     * @param time 时间字符串
     * @return String
     */
    public static String transTimeChat(String time, String nowTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = sdf.parse(time);
            System.out.println(date);
            long datel = date.getTime();
            Date nowl = sdf.parse(nowTime);

            long d = (nowl.getTime() - datel) / 1000;//单位:s
            if (d > 0) {
                if (d < 60) {//小于一分钟  提示  几秒前
                    return d + "秒前";
                }
                if (d < 60 * 60) {//小于一小时  提示  几分钟前
                    return d / (60) + "分钟前";
                }
                if (d < 60 * 60 * 24) {//小于一天  提示  几小时前
                    return d / (60 * 60) + "小时前";
                }
                if (d < 60 * 60 * 24 * 30) {//小于一月  提示  几天前
                    return d / (60 * 60 * 24) + "天前";
                }
                if (d < 60 * 60 * 24 * 7 * 30 * 12) {//小于一年  提示  几月前
                    return d / (60 * 60 * 24 * 30) + "月前";
                }
                if (d > 60 * 60 * 24 * 7 * 30 * 12) {//大于一年  提示  几年前
                    return d / (60 * 60 * 24 * 30 * 12) + "年前";
                }

            } else {
                return "1秒前";
            }


        } catch (Exception e) {
            return "";
        }
        return "";

    }

    /**
     * 转换时间显示形式(与当前系统时间比较),在显示即时聊天的时间时使用
     *
     * @param time 时间字符串
     * @return String
     */
    public static String transTimeChat(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = sdf.parse(time);
            System.out.println(date);
            long datel = date.getTime();
            Date nowl = Calendar.getInstance().getTime();

            long d = (nowl.getTime() - datel) / 1000;//单位:s
            if (d > 0) {
                if (d < 60) {//小于一分钟  提示  几秒前
                    return d + "秒前";
                }
                if (d < 60 * 60) {//小于一小时  提示  几分钟前
                    return d / (60) + "分钟前";
                }
                if (d < 60 * 60 * 24) {//小于一天  提示  几小时前
                    return d / (60 * 60) + "小时前";
                }
                if (d < 60 * 60 * 24 * 30) {//小于一月  提示  几天前
                    return d / (60 * 60 * 24) + "天前";
                }
                if (d < 60 * 60 * 24 * 7 * 30 * 12) {//小于一年  提示  几月前
                    return d / (60 * 60 * 24 * 30) + "月前";
                }
                if (d > 60 * 60 * 24 * 7 * 30 * 12) {//大于一年  提示  几年前
                    return d / (60 * 60 * 24 * 30 * 12) + "年前";
                }

            } else {
                return "1秒前";
            }


        } catch (Exception e) {
            return "";
        }
        return "";

    }


    /**
     * 返回10-15
     *
     * @param date 2011-10-15形式
     * @return 10-15
     */
    public static String subDate(String date) {
        if (date.indexOf("-") > -1) {
            return date.substring(date.indexOf("-") + 1);
        } else {
            return date;
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static String getSize(long length) {
        long k = length / 1024;
        if (k < 1024)
            return k + "K";
        else {
            double m = (double) k / (double) 1024;
            if (m < 1024) {
                String ms = String.format(Locale.getDefault(), "%.2f", m);
                return ms + "M";
            } else {
                double g = (double) m / (double) 1024;
                if (g < 1024) {
                    String gs = String.format(Locale.getDefault(), "%.2f", g);
                    return gs + "G";
                } else {
                    return ">1T";
                }
            }
        }
    }

    /**
     * 保留2位小数
     *
     * @param num
     * @return
     */
    public static String formatToNumber(float num) {
        DecimalFormat df = new DecimalFormat("##0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        BigDecimal obj = new BigDecimal(num);
        if (obj.compareTo(BigDecimal.ZERO) == 0) {
            return "0.00";
        } else if (obj.compareTo(BigDecimal.ZERO) > 0 && obj.compareTo(new BigDecimal(1)) < 0) {
            return "0" + df.format(obj).toString();
        } else {
            return df.format(obj).toString();
        }

    }

    /**
     * 重新计算listView的高度
     *
     * @param listView
     */
    public static int setListViewHeightBasedOnChildren(ListView listView, int moreHeight) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        if (listAdapter.getCount() > 0) {
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
        }
        totalHeight += moreHeight;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        return params.height;

    }

    /**
     * 重新计算GridView的高度
     */
    @SuppressLint("NewApi")
    public static int setGridViewHeightBasedOnChildren(GridView gridView, int moreHeight) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        int lnum = listAdapter.getCount() / 3 + (listAdapter.getCount() % 3 > 0 ? 1 : 0);
        if (listAdapter.getCount() > 0) {
            for (int i = 0; i < lnum; i++) {
                View listItem = listAdapter.getView(i, null, gridView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
        }

        totalHeight += moreHeight;
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + (gridView.getVerticalSpacing() * lnum);
        gridView.setLayoutParams(params);
        return params.height;

    }

    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int rows;
        int columns = 0;
        int horizontalBorderHeight = 0;
        Class<?> clazz = gridView.getClass();
        try {
            //利用反射，取得每行显示的个数
            Field column = clazz.getDeclaredField("mRequestedNumColumns");
            column.setAccessible(true);
            columns = (Integer) column.get(gridView);
            //利用反射，取得横向分割线高度
            Field horizontalSpacing = clazz.getDeclaredField("mRequestedHorizontalSpacing");
            horizontalSpacing.setAccessible(true);
            horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
        columns = 4;
        if (listAdapter.getCount() % columns > 0) {
            rows = listAdapter.getCount() / columns + 1;
        } else {
            rows = listAdapter.getCount() / columns;
        }
        int totalHeight = 0;
        for (int i = 0; i < rows; i++) { //只计算每项高度*行数
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + horizontalBorderHeight * (rows - 1);//最后加上分割线总高度
        gridView.setLayoutParams(params);
    }

    //强制显示或者关闭系统键盘
    public static void KeyBoardOpen(final View view) {
//	        Timer timer = new Timer();
//	        timer.schedule(new TimerTask(){
//	        @Override
//	        public void run(){
//
//	         	}
//	         }, 300);
        InputMethodManager m = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        m.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    //强制显示或者关闭系统键盘
    public static void KeyBoardClose(final View view) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }, 300);
    }


    /**
     * 获得指定日期的前一天
     *
     * @return
     * @throws Exception
     */
    public static Date getSpecifiedDayBefore(Date dateNow) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        Date date = dateNow;
//	        try {
//	            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
//	        } catch (ParseException e) {
//	            e.printStackTrace();
//	        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());

        return c.getTime();
    }

    public static float getMax(Float[] data) {
        int i;
        float min, max;
        Float A[] = data;  // 声明整数数组A,并赋初值

        min = max = A[0];
        System.out.print("数组A的元素包括：");
        for (i = 0; i < A.length; i++) {
            System.out.print(A[i] + " ");
            if (A[i] > max)   // 判断最大值
                max = A[i];
            if (A[i] < min)   // 判断最小值
                min = A[i];
        }
        return max;
    }

    public static float getMin(Float[] data) {
        int i;
        float min, max;
        Float A[] = data;  // 声明整数数组A,并赋初值

        min = max = A[0];
        System.out.print("数组A的元素包括：");
        for (i = 0; i < A.length; i++) {
            System.out.print(A[i] + " ");
            if (A[i] > max)   // 判断最大值
                max = A[i];
            if (A[i] < min)   // 判断最小值
                min = A[i];
        }
        return min;
    }

    /**
     * 判断是否是100的整数倍
     *
     * @return
     */
    public static boolean is100(int num) {
        int y = num % 100;
        if (y == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String transDistance(float distance) {
        String ds = "";
        if (distance < 1000) {
            ds += (distance + "米");
        } else {
            float km = distance / 1000;
            ds += (String.format(Locale.getDefault(), "%.2f", km) + "千米");
        }
        return ds;
    }

    /**
     * 计算两点间的距离
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return km
     */
    public static String getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return formatSmallNumber((float) s) + "km";
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 保留2位小数
     *
     * @param num
     * @return
     */
    public static float formatSmallNumber(float num) {
        DecimalFormat decimalFormat = new DecimalFormat("##0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        float p = Float.valueOf(decimalFormat.format(num));//format 返回的是字符串
        return p;
    }

    /**
     * 计算两点间的距离
     *
     * @param lat1 纬度
     * @param lng1 经度
     * @param lat2 纬度
     * @param lng2 经度
     * @return
     */
    public static double getDistances(double lat1, double lng1, double lat2,
                                      double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public static void setBackgroundAlpha(Activity mContext, float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) mContext).getWindow().setAttributes(lp);
    }


    /**
     * 判断本地头像是否是最新
     */
    private static boolean isNew(Context context, String url) {
        SharedPreferences preferences = context.getSharedPreferences("portrait", Context.MODE_PRIVATE);
        return preferences.getString("url", "").equals(url);
    }

    // 隐藏键盘
    public static void hideInput(Activity context) {
        if (context != null && context.getCurrentFocus() != null
                && context.getCurrentFocus().getWindowToken() != null) {
            ((InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(context.getCurrentFocus()
                            .getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // 显示键盘
    public static void showInput(Activity context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 模拟点击view
     *
     * @param view
     */
    public static void touchView(final View view) {

        view.postDelayed(new Runnable() {

            @Override
            public void run() {

                // TODO Auto-generated method stub
                view.dispatchTouchEvent(MotionEvent.obtain(
                        SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN, view.getLeft() + 5,
                        view.getTop() + 5, 0));
                view.dispatchTouchEvent(MotionEvent.obtain(
                        SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_UP, view.getLeft() + 5,
                        view.getTop() + 5, 0));
            }
        }, 300);
    }

    public static void touchViewCenter(final View view) {

        view.postDelayed(new Runnable() {

            @Override
            public void run() {

                // TODO Auto-generated method stub
                view.dispatchTouchEvent(MotionEvent.obtain(
                        SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_DOWN, view.getLeft() + view.getWidth() / 2,
                        view.getTop() + view.getHeight() / 2, 0));
                view.dispatchTouchEvent(MotionEvent.obtain(
                        SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                        MotionEvent.ACTION_UP, view.getLeft() + view.getWidth() / 2,
                        view.getTop() + view.getHeight() / 2, 0));
            }
        }, 300);
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    /**
     * 将unicode的汉字码转换成utf-8格式的汉字
     *
     * @param unicodeStr
     * @return
     */
    public static String unicodeToString(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }

    public static String getDeviceId(Context context) {


        StringBuilder deviceId = new StringBuilder();
        // 渠道标志
        deviceId.append("a");

        try {

//wifi mac地址
//            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//            WifiInfo info = wifi.getConnectionInfo();
//            String wifiMac = info.getMacAddress();
//            if (!isEmpty(wifiMac)) {
//                deviceId.append("wifi");
//                deviceId.append(wifiMac);
//
//                return deviceId.toString();
//            }


            //IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission")
            String imei = tm.getDeviceId();
            if (!isEmpty(imei)) {
                deviceId.append("imei");
                deviceId.append(imei);
                return deviceId.toString();
            }

            //序列号（sn）
            @SuppressLint("MissingPermission")
            String sn = tm.getSimSerialNumber();
            if (!isEmpty(sn)) {
                deviceId.append("sn");
                deviceId.append(sn);
                return deviceId.toString();
            }
            String macAddress = null;
            StringBuffer buf = new StringBuffer();
            NetworkInterface networkInterface = null;
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
//            if (networkInterface == null) {
//                return "02:00:00:00:00:00";
//            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
            Log.e("eee", "getDeviceId: " + macAddress);
            if (!isEmpty(macAddress)) {
                return macAddress;
            }
            //如果上面都没有， 则生成一个id：随机码
            String uuid = getUUID(context);
            if (!isEmpty(uuid)) {
                deviceId.append("id");
                deviceId.append(uuid);
                return deviceId.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id").append(getUUID(context));
        }


        return deviceId.toString();

    }

    /**
     * 得到全局唯一UUID
     */
    public static String getUUID(Context context) {
        String uuid = SharedPreferencesUtil.get(context, "sysCacheMap");
        if (isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            SharedPreferencesUtil.save(context, "sysCacheMap", uuid);
        }
        return uuid;
    }


    public interface OnCodeResult {
        void done(Bitmap bitmap);
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
     * 比较app版本是否需要升级
     *
     * @param current
     * @param service
     * @return
     */
    public static boolean isNeedUpDate(String current, String service) {
        if (current == null || service == null || current.isEmpty() || service.isEmpty())
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
}