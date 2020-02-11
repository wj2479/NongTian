package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.bean.SignListInfo;
import com.qdhc.ny.view.CRatingBar;
import com.sj.core.utils.ImageLoaderUtil;

import java.util.List;

/**
 * 签到内容列表
 */

public class SignListAdapter extends BaseQuickAdapter<SignListInfo.DataListBean, BaseViewHolder> {
    Activity mContext;

    public SignListAdapter(Activity mContext, @Nullable List<SignListInfo.DataListBean> data) {
        super(R.layout.item_sign_count, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, SignListInfo.DataListBean item) {

        //图片
        ImageView logoview = helper.getView(R.id.iv_photo);
        ImageLoaderUtil.INSTANCE.loadCorners(mContext, item.getImg(), logoview, -1, R.drawable.ic_defult_user);
        //标题
        helper.setText(R.id.tv_title, item.getNickname());
        //时间
        helper.setText(R.id.tv_time, item.getAddDate());

        helper.setText(R.id.tv_customer, "跟进客户：" + (item.getPersonName().isEmpty() ? "无" : item.getPersonName()));
        //客户手机
        helper.setText(R.id.tv_phone, "客户手机：" + item.getPersonPhone());
        //客户评价
        CRatingBar crb = helper.getView(R.id.rb);
        if (item.getCommentScore()==0){
            crb.setVisibility(View.GONE);
            helper.setVisible(R.id.tv_evaluate_empty,true);
        }else{
            crb.setVisibility(View.VISIBLE);
            helper.setVisible(R.id.tv_evaluate_empty,false);
        }
        crb.setStar(item.getCommentScore());
        //跟进地址
        helper.setText(R.id.tv_address, "跟进地址：" + item.getAddressAuto());
        //签到类型
        helper.setText(R.id.tv_address, "签到类型：" + item.getCategory());


    }

}
