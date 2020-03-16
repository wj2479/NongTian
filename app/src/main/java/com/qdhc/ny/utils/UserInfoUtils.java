package com.qdhc.ny.utils;

import com.google.gson.Gson;
import com.qdhc.ny.base.BaseApplication;
import com.qdhc.ny.entity.User;
import com.sj.core.net.Rx.RxRestClient;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author wj
 * @Date 2019/11/15
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class UserInfoUtils {

    /**
     * 根据用户ID 获取用户信息
     *
     * @param uid
     * @param runnable
     */
    public static void getInfoByObjectId(final int uid, final IResult runnable) {
        if (uid <= 0) {
            runnable.onReslt(null);
            return;
        }

        User userInfo = BaseApplication.userInfoMap.get(uid);
        if (userInfo == null) {
            getUserById(uid, runnable);
        } else {
            runnable.onReslt(userInfo);
        }
    }

    public interface IResult {
        void onReslt(User userInfo);
    }

    /**
     * 获取用户信息
     *
     * @param uid
     * @param runnable
     */
    static void getUserById(int uid, final IResult runnable) {
        if (runnable == null)
            return;
        HashMap<String, Object> params = new HashMap();
        params.put("uid", uid);
        RxRestClient.create()
                .url("user/getUserById")
                .params(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        JSONObject json = new JSONObject(s);
                        if (json.getInt("code") == 1000) {
                            JSONObject jsonObject = json.getJSONObject("result");
                            User user = new Gson().fromJson(jsonObject.toString(), User.class);
                            runnable.onReslt(user);
                        } else {
                            runnable.onReslt(null);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        runnable.onReslt(null);
                    }
                });
    }

}
