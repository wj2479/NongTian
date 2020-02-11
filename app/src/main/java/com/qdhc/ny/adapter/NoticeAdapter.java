package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.bmob.Notice;

import java.util.List;

/**
 * 通知列表
 */

public class NoticeAdapter extends BaseQuickAdapter<Notice, BaseViewHolder> {
    Activity mContext;

    public NoticeAdapter(Activity mContext, @Nullable List<Notice> data) {
        super(R.layout.item_notice, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, Notice item) {
        //标题
        helper.setText(R.id.tv_title, item.getTitle());
       //内容
        helper.setText(R.id.tv_time,item.getCreatedAt());

    }

}
