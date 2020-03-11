package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.entity.Project;
import com.qdhc.ny.utils.BaseUtil;

import java.util.List;

/**
 * 信息列表
 */

public class ProjectAdapter extends BaseQuickAdapter<Project, BaseViewHolder> {
    Activity mContext;

    public ProjectAdapter(Activity mContext, @Nullable List<Project> data) {
        super(R.layout.item_project, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, Project item) {
        helper.setText(R.id.tv_title, item.getName());

        helper.setText(R.id.tv_status, item.getProcess() + "%");
        helper.setText(R.id.tv_introduce, item.getInfo());
////        helper.setText(R.id.tv_village, item.getVillage());
////        helper.setText(R.id.tv_district, item.getDistrict());
        if (item.getManager() != null) {
            helper.setText(R.id.tv_person, "负责人:" + item.getManager().getNickName());
        } else {
            helper.setText(R.id.tv_person, "负责人: 未知");
        }
//
        if (TextUtils.isEmpty(item.getUpdateTime())) {
            if (!TextUtils.isEmpty(item.getCreateTime())) {
                helper.setText(R.id.tv_from, "创建时间: " + BaseUtil.shortDate(item.getCreateTime()));
            } else {
                helper.setText(R.id.tv_from, "更新时间: " + BaseUtil.shortDate(item.getUpdateTime()));
            }

        } else {
            helper.setText(R.id.tv_from, "更新时间: " + BaseUtil.shortDate(item.getUpdateTime()));
        }

    }

}
