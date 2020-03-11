package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;

import java.util.List;

/**
 * 信息列表
 */

public class SignAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    Activity mContext;

    public SignAdapter(Activity mContext, @Nullable List<String> data) {
        super(R.layout.item_sign, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {

    }

}
