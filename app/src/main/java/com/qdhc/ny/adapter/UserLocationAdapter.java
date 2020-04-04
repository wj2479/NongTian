package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.entity.UserLastLocation;

import java.util.List;

/**
 * 用户位置
 */
public class UserLocationAdapter extends BaseQuickAdapter<UserLastLocation, BaseViewHolder> {
    Activity mContext;

    int selectIndex = -1;

    public UserLocationAdapter(Activity mContext, @Nullable List<UserLastLocation> data) {
        super(R.layout.item_user_location, data);
        this.mContext = mContext;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserLastLocation item) {
        helper.setText(R.id.tv_title, item.getNickName());
        ImageView lineIv = helper.getView(R.id.lineIv);
        TextView contentTv = helper.getView(R.id.tv_content);

        helper.addOnClickListener(R.id.tracksBut);
        if (item.getLat() != 0 && item.getLng() != 0) {
            helper.setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.light_black));
            if (selectIndex == helper.getAdapterPosition()) {
                lineIv.setVisibility(View.VISIBLE);
                helper.setBackgroundColor(R.id.locationLayout, ContextCompat.getColor(mContext, R.color.color_f0));
            } else {
                lineIv.setVisibility(View.INVISIBLE);
                helper.setBackgroundColor(R.id.locationLayout, ContextCompat.getColor(mContext, R.color.white));
            }
            try {
                contentTv.setText(item.getCreateTime().substring(11));
            } catch (Exception e) {
            }


        } else {
            helper.setTextColor(R.id.tv_title, ContextCompat.getColor(mContext, R.color.c));
            lineIv.setVisibility(View.INVISIBLE);
            helper.setBackgroundColor(R.id.locationLayout, ContextCompat.getColor(mContext, R.color.white));
            contentTv.setText("今日暂无位置");
        }
    }

}
