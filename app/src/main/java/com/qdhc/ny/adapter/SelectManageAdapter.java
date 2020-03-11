package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.entity.User;

import java.util.List;

/**
 * 跟通讯录一个
 * 选择运管员
 */

public class SelectManageAdapter extends BaseQuickAdapter<User, BaseViewHolder> {
    Activity mContext;

    public SelectManageAdapter(Activity mContext, @Nullable List<User> data) {
        super(R.layout.item_select_manager, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {



    }

}
