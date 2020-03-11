package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;

import java.util.List;

/**
 * 通知列表
 */

public class NoticeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    Activity mContext;

    public NoticeAdapter(Activity mContext, @Nullable List<String> data) {
        super(R.layout.item_notice, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }

}
