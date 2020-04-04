package com.sj.core.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 申健 on 2018/8/12.
 */
public class ToastUtil {

    private static Toast toast;

    public static void show(Context context, String info) {
        if (toast == null) {
            toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
        } else {
            toast.setText(info);
        }
        toast.show();
    }

    public static void show(Context context, int info) {
        if (toast == null) {
            toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
        } else {
            toast.setText(info);
        }
        toast.show();
    }

}
