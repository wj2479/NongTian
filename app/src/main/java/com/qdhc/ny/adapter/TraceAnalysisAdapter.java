package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.bean.TraceAnalysisInfo;

import java.util.List;

/**
 *轨迹分析
 */

public class TraceAnalysisAdapter extends BaseQuickAdapter<TraceAnalysisInfo, BaseViewHolder> {
    Activity mContext;

    public TraceAnalysisAdapter(Activity mContext, @Nullable List<TraceAnalysisInfo> data) {
        super(R.layout.item_location_analysis, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, TraceAnalysisInfo item) {

        //图片
        helper.setImageResource(R.id.iv_photo,item.getImgSrc());
        helper.setText(R.id.tv_title, item.getCategory()+"("+item.getAddDate()+")");
        helper.setText(R.id.tv_content, item.getAddressAuto());




    }

}
