package com.qdhc.ny.utils;

import android.content.Context;

import com.qdhc.ny.entity.User;
import com.sj.core.utils.SharedPreferencesUtil;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @Author wj
 * @Date 2019/7/31
 * @Desc
 */
public class SharedPreferencesUtils extends SharedPreferencesUtil {

    public static final String LOGIN = "LOGIN";

    /**
     * 保存设置的SLAM地图
     *
     * @param ctx
     */
    public static void saveLogin(Context ctx, User userInfo) {
        try {
            File file = new File(ctx.getFilesDir(), LOGIN);
            if (file.exists()) {
                file.delete();
            }
            ObjectOutputStream oos = new ObjectOutputStream(ctx.openFileOutput(LOGIN, Context.MODE_PRIVATE));
            oos.writeObject(userInfo);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载已保存的登录信息
     *
     * @param ctx
     * @return
     */
    public static User loadLogin(Context ctx) {
        User data = null;
        try {
            File file = new File(ctx.getFilesDir(), LOGIN);
            if (file.exists()) {
                ObjectInputStream ois = new ObjectInputStream(ctx.openFileInput(LOGIN));
                data = (User) ois.readObject();
                ois.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null) {
            data = new User();
        }
        return data;
    }

    public static void removeLogin(Context ctx) {
        try {
            File file = new File(ctx.getFilesDir(), LOGIN);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
