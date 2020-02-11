package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.bmob.Project;

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

        helper.setText(R.id.tv_status, "进度: " + item.getSchedule() + "%");
        helper.setText(R.id.tv_introduce, item.getIntroduce());
//        helper.setText(R.id.tv_village, item.getVillage());
//        helper.setText(R.id.tv_district, item.getDistrict());

        helper.setText(R.id.tv_from, "创建时间: " + item.getCreatedAt().substring(0, 10));

    }

}
