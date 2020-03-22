package com.qdhc.ny.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.entity.ReportComment;
import com.qdhc.ny.entity.User;
import com.qdhc.ny.utils.UserInfoUtils;

import java.util.List;

/**
 * 通知列表
 */
public class ReportCommentAdapter extends BaseMultiItemQuickAdapter<ReportComment, BaseViewHolder> {

    Context mContext;

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ReportCommentAdapter(List<ReportComment> data) {
        super(data);
        addItemType(ReportComment.TYPE_LEFT, R.layout.adapter_speak_content_left);
        addItemType(ReportComment.TYPE_RIGHT, R.layout.adapter_speak_content_right);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ReportComment item) {
        String url = "";
        if (item.getMediaList() != null && item.getMediaList().size() > 0) {
            url = item.getMediaList().get(0).getUrl();
        }

        switch (helper.getItemViewType()) {
            case ReportComment.TYPE_LEFT:
                UserInfoUtils.getInfoByObjectId(item.getSender(), new UserInfoUtils.IResult() {
                    @Override
                    public void onReslt(User userInfo) {
                        if (userInfo != null) {
                            helper.setText(R.id.nameTv, userInfo.getNickName());
                            helper.setText(R.id.levelTv, userInfo.getRole().getDesc());
                        }
                    }
                });
                break;
            case ReportComment.TYPE_RIGHT:
                break;
        }

        helper.setText(R.id.timeTv, item.getCreateTime().substring(5));

        TextView contentTv = helper.getView(R.id.contentTv);
        if (item.getContent().isEmpty()) {
            contentTv.setVisibility(View.GONE);
        } else {
            contentTv.setVisibility(View.VISIBLE);
            contentTv.setText(item.getContent());
        }
        ImageView picIv = helper.getView(R.id.picIv);
        if (url.isEmpty()) {
            picIv.setVisibility(View.GONE);
        } else {
            picIv.setVisibility(View.VISIBLE);
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_default_pic);
            Glide.with(mContext).load(url).apply(requestOptions).into(picIv);
            helper.addOnClickListener(R.id.picIv);
        }
    }

}
