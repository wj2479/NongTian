package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;

import java.util.List;

/**
 * 选择银行列表
 */

public class BankAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    Activity mContext;

    public BankAdapter(Activity mContext, @Nullable List<String> data) {
        super(R.layout.item_bank, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        //标题
        helper.setText(R.id.tv_title, item);
    }

}
