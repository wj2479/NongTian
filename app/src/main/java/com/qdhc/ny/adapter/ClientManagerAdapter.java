package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.bean.ClientManagerInfo;

import java.util.List;

/**
 *
 * 我的客户经理
 *
 */

public class ClientManagerAdapter extends BaseQuickAdapter<ClientManagerInfo, BaseViewHolder> {
    Activity mContext;

    public ClientManagerAdapter(Activity mContext, @Nullable List<ClientManagerInfo> data) {
        super(R.layout.item_client_manager, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, ClientManagerInfo item) {
        helper.setText(R.id.tv_surname, item.getSurName());
        helper.setText(R.id.tv_name, item.getPersonName()+"("+item.getPersonNum()+")"+item.getPersonSex());
        helper.setText(R.id.tv_time,item.getPifuDate());
        helper.setText(R.id.tv_phone,item.getPhone());
        helper.setText(R.id.tv_bank,item.getFenhang()+"/"+item.getZhihang());
//        //图片
//        ImageView logoview = helper.getView(R.id.iv_photo);
//        ImageLoaderUtil.INSTANCE.load(mContext, item.getAddDate(), logoview);
//        //标题
//        helper.setText(R.id.tv_title, item.getTitle());
//       //内容
//        helper.setText(R.id.tv_content, item.getGonggaoContent());
//        helper.setText(R.id.tv_time,item.getAddDate());


    }

}
