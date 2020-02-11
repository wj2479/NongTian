package com.qdhc.ny.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.bmob.Notify;

import java.util.List;

/**
 * 通知列表
 */

public class NotifyReceivedAdapter extends BaseQuickAdapter<Notify, BaseViewHolder> {
    Activity mContext;

    public NotifyReceivedAdapter(Activity mContext, @Nullable List<Notify> data) {
        super(R.layout.item_notify_receive, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, Notify item) {
        //标题
        helper.setText(R.id.tv_title, item.getContent());
        //时间
        helper.setText(R.id.tv_time, item.getCreatedAt().substring(0, 10));
        //状态
        helper.setText(R.id.tv_status, item.isRead() ? "已读" : "未读");
        // 颜色
        helper.setTextColor(R.id.tv_status, item.isRead() ? Color.parseColor("#8c8c8c") : Color.parseColor("#aae84e40"));

    }

}
