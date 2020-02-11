package com.sj.core.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 申健 on 2018/8/12.
 */

public class ToastUtil {

    public static void show(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

    public static void show(Context context, int info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

}
