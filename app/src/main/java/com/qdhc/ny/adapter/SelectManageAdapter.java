package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.bmob.UserInfo;
import com.sj.core.utils.ImageLoaderUtil;

import java.util.List;

/**
 * 跟通讯录一个
 * 选择运管员
 */

public class SelectManageAdapter extends BaseQuickAdapter<UserInfo, BaseViewHolder> {
    Activity mContext;

    public SelectManageAdapter(Activity mContext, @Nullable List<UserInfo> data) {
        super(R.layout.item_select_manager, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfo item) {
        if (item.getAvatar().getFileUrl().length()>6){//图片
            helper.setVisible(R.id.iv_photo,true);
            helper.setVisible(R.id.tv_photo,false);
            ImageView logoview = helper.getView(R.id.iv_photo);
            ImageLoaderUtil.INSTANCE.loadCorners(mContext, item.getAvatar().getFileUrl(), logoview,-1,R.drawable.ic_defult_user);
        }else{
            helper.setVisible(R.id.iv_photo,false);
            helper.setVisible(R.id.tv_photo,true);
            if ( item.getNickName().length()>=2){
                helper.setText(R.id.tv_photo,item.getNickName()
                        .substring(item.getNickName().length()-2,item.getNickName().length()));
            }
        }
        //姓名
        helper.setText(R.id.tv_name, item.getNickName());
        //手机号
        helper.setText(R.id.tv_phone, item.getMobilePhoneNumber());



    }

}
