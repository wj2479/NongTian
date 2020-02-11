package com.qdhc.ny.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.activity.SignInListActivity;
import com.qdhc.ny.activity.TraceRecordActivity;
import com.qdhc.ny.bmob.UserInfo;

import java.util.List;

/**
 * 通讯录
 */

public class ContactsAdapter extends BaseQuickAdapter<UserInfo, BaseViewHolder> {
    Activity mContext;

    public ContactsAdapter(Activity mContext, @Nullable List<UserInfo> data) {
        super(R.layout.item_contacts, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, final UserInfo item) {
        //姓名
        helper.setText(R.id.tv_name, item.getNickName());
        //手机号
        helper.setText(R.id.tv_phone, "手机号:" + item.getMobilePhoneNumber() == null ? "未知" : item.getMobilePhoneNumber());
        //用户名
        helper.setText(R.id.tv_username, "用户名:" + item.getUsername());

        if (item.getAvatar() != null) {
            RequestOptions mRequestOptions = RequestOptions.bitmapTransform(new CircleCrop()).placeholder(R.drawable.ic_defult_user)
                    .error(R.drawable.ic_defult_user);

            Glide.with(mContext).load(item.getAvatar().getUrl())
                    .apply(mRequestOptions)
                    .into((ImageView) helper.getView(R.id.iv_photo));
        }

        helper.setOnClickListener(R.id.tv_sign, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SignInListActivity.class);
                intent.putExtra("userInfo", item);
                mContext.startActivity(intent);
            }
        });
        helper.setOnClickListener(R.id.tv_track, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TraceRecordActivity.class);
                intent.putExtra("userInfo", item);
                mContext.startActivity(intent);
            }
        });

    }

}
