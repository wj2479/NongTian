package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.bmob.UserInfo;

import java.util.ArrayList;

/**
 * 通讯录
 */

public class AchievementAdapter extends BaseQuickAdapter<UserInfo, BaseViewHolder> {
    Activity mContext;

    public AchievementAdapter(Activity mContext, @Nullable ArrayList<UserInfo> data) {
        super(R.layout.item_achievement_ranking, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfo item) {
        TextView tvNo = helper.getView(R.id.tv_no);


        switch (helper.getLayoutPosition()) {
            case 0:
                tvNo.setText("");
                tvNo.setCompoundDrawablesWithIntrinsicBounds(
                        ContextCompat.getDrawable(mContext, R.drawable.ic_first), null, null, null);
                break;
            case 1:
                tvNo.setText("");
                tvNo.setCompoundDrawablesWithIntrinsicBounds(
                        ContextCompat.getDrawable(mContext, R.drawable.ic_second), null, null, null);
                break;
            case 2:
                tvNo.setText("");
                tvNo.setCompoundDrawablesWithIntrinsicBounds(
                        ContextCompat.getDrawable(mContext, R.drawable.ic_third), null, null, null);
                break;
            default:
                tvNo.setText(" " +( helper.getLayoutPosition()+1));
                tvNo.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                break;
        }

        //姓名
        helper.setText(R.id.tv_name, item.getNickName());
        //头像
        ImageView logoview = helper.getView(R.id.iv_photo);
//        ImageLoaderUtil.INSTANCE.loadCorners(mContext, item.getHeadimg(), logoview, -1, R.drawable.ic_defult_user);
        //   ImageLoaderUtil.INSTANCE.load(mContext, item.getHeadimg(), logoview);
        //分数
//        helper.setText(R.id.tv_score, item.getUserScore() + "");


    }

}
