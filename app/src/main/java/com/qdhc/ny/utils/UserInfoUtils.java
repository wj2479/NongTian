package com.qdhc.ny.utils;

import android.text.TextUtils;

import com.qdhc.ny.base.BaseApplication;
import com.qdhc.ny.bmob.UserInfo;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

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
     * @param objectId
     * @param runnable
     */
    public static void getInfoByObjectId(final String objectId, final IResult runnable) {
        if (TextUtils.isEmpty(objectId)) {
            runnable.onReslt(null);
            return;
        }

        UserInfo userInfo = BaseApplication.userInfoMap.get(objectId);
        if (userInfo == null) {
            BmobQuery bmobQuery = new BmobQuery<UserInfo>();
            bmobQuery.getObject(objectId, new QueryListener<UserInfo>() {
                @Override
                public void done(UserInfo uInfo, BmobException e) {
                    if (e == null) {
                        runnable.onReslt(uInfo);
                        BaseApplication.userInfoMap.put(objectId, uInfo);
                    } else {
                        runnable.onReslt(null);
                    }
                }
            });
        } else {
            runnable.onReslt(userInfo);
        }
    }

    public interface IResult {
        void onReslt(UserInfo userInfo);
    }
}
