package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.bmob.Sign;

import java.util.List;

/**
 * 信息列表
 */

public class SignAdapter extends BaseQuickAdapter<Sign, BaseViewHolder> {
    Activity mContext;

    public SignAdapter(Activity mContext, @Nullable List<Sign> data) {
        super(R.layout.item_sign, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, Sign item) {
        helper.setText(R.id.tv_title, "时间: " + item.getCreatedAt());

        helper.setText(R.id.tv_introduce, "位置:  " + item.getAddress());
//        helper.setText(R.id.tv_village, item.getVillage());
//        helper.setText(R.id.tv_district, item.getDistrict());
//        helper.setText(R.id.tv_content, item.getDescription());
//        helper.setText(R.id.tv_from, "" + item.getFrom());

    }

}
