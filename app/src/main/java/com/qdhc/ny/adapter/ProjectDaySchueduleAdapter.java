package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.entity.DaySchedule;

import java.util.List;

/**
 * 项目进度
 */
public class ProjectDaySchueduleAdapter extends BaseQuickAdapter<DaySchedule, BaseViewHolder> {
    Activity mContext;

    public ProjectDaySchueduleAdapter(Activity mContext, @Nullable List<DaySchedule> data) {
        super(R.layout.item_project_day_schedule, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, DaySchedule item) {
        helper.setText(R.id.tv_title, item.getDate());
        helper.setText(R.id.tv_status, item.getSchedule() + "%");

        if (item.getLastMedia() != null) {
            ImageView imgIv = helper.getView(R.id.iv_img);
            Glide.with(mContext).load(item.getLastMedia().getUrl()).into(imgIv);
        } else {
            helper.setText(R.id.noPicTv, "暂无图片数据");
        }
        helper.setText(R.id.tv_introduce, item.getUpdateTime());
    }

}
