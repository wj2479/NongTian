package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.entity.DailyReport;

import java.util.List;

/**
 * 项目进度
 */
public class ProjectSchueduleAdapter extends BaseQuickAdapter<DailyReport, BaseViewHolder> {
    Activity mContext;

    public ProjectSchueduleAdapter(Activity mContext, @Nullable List<DailyReport> data) {
        super(R.layout.item_project_schedule, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, DailyReport item) {
        String createTime = "";
//        if (item.getCreatedAt() != null && item.getCreatedAt().length() > 3) {
//            createTime = item.getCreatedAt().substring(0, item.getCreatedAt().length() - 3);
//        } else {
//            createTime = item.getRemark().substring(0, item.getRemark().length() - 3);
//        }

        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_status, "进度:" + item.getSchedule() + "%");
        helper.setText(R.id.tv_introduce, item.getContent());
    }

}
