package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.bmob.Notify;

import java.util.List;

/**
 * 通知列表
 */

public class NotifyAdapter extends BaseQuickAdapter<Notify, BaseViewHolder> {
    Activity mContext;

    public NotifyAdapter(Activity mContext, @Nullable List<Notify> data) {
        super(R.layout.item_notice, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, Notify item) {
        //标题
        helper.setText(R.id.tv_title, item.getContent());
        //内容
        helper.setText(R.id.tv_time, item.getCreatedAt().substring(0, 10));

    }

}
