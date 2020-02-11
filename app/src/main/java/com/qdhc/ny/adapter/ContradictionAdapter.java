package com.qdhc.ny.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.qdhc.ny.R;
import com.qdhc.ny.base.BaseApplication;
import com.qdhc.ny.bmob.Contradiction;
import com.qdhc.ny.bmob.UserInfo;
import com.qdhc.ny.common.ProjectData;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * 信息列表
 */

public class ContradictionAdapter extends BaseQuickAdapter<Contradiction, BaseViewHolder> {
    Activity mContext;

    public ContradictionAdapter(Activity mContext, @Nullable List<Contradiction> data) {
        super(R.layout.item_message, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, Contradiction item) {
        helper.setText(R.id.tv_title, item.getFrom());

        helper.setText(R.id.tv_village, item.getVillage());
        helper.setText(R.id.tv_district, item.getDistrict());
        helper.setText(R.id.tv_content, item.getDescription());
        helper.setText(R.id.tv_from, "" + item.getFrom());


        if ("已处理".equals(item.getStatus())) {
            helper.setTextColor(R.id.tv_status, Color.parseColor("#4698D1"));
            helper.setText(R.id.tv_status, item.getStatus());
        } else {
            helper.setTextColor(R.id.tv_status, Color.RED);
            helper.setText(R.id.tv_status, "未处理");
        }

        Pattern pattern = Pattern.compile("\\d{2}-\\d{2} \\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(item.getCreatedAt());
        if (matcher.find()) {
            String time = matcher.group(0);
            helper.setText(R.id.tv_time, time);
        } else {
            helper.setText(R.id.tv_time, item.getCreatedAt());
        }

        helper.setText(R.id.tv_type, "类别: " + item.getType());

        Log.e("TAG", new Gson().toJson(item.getUploader()));

        if (ProjectData.getInstance().getUserInfo().getRole() == 1) {
            helper.setVisible(R.id.tv_upload, false);
        } else {
            helper.setVisible(R.id.tv_upload, true);
            UserInfo userInfo = BaseApplication.userInfoMap.get(item.getUploader());
            if (userInfo == null) {
                BmobQuery<UserInfo> bmobQuery = new BmobQuery<>();
                bmobQuery.getObject(item.getUploader(), new QueryListener<UserInfo>() {
                    @Override
                    public void done(UserInfo uInfo, BmobException e) {
                        if (e == null) {
                            helper.setText(R.id.tv_upload, "上报人: " + uInfo.getNickName());
                            BaseApplication.userInfoMap.put(uInfo.getObjectId(), uInfo);
                        } else {
                            helper.setText(R.id.tv_upload, "上报人: 未知");
                        }
                    }
                });
            } else {
                helper.setText(R.id.tv_upload, "上报人: " + userInfo.getNickName());
            }
        }

    }

}
