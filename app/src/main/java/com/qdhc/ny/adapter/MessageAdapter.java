package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.bean.MessageInfo;

import java.util.List;

/**
 * 通知列表
 */

public class MessageAdapter extends BaseQuickAdapter<MessageInfo, BaseViewHolder> {
    Activity mContext;

    public MessageAdapter(Activity mContext, @Nullable List<MessageInfo> data) {
        super(R.layout.item_message, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageInfo item) {
        //标题


    }

}
