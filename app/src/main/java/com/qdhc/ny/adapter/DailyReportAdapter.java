package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.entity.DailyReport;

import java.util.Calendar;
import java.util.List;

/**
 * 新日报列表
 */
public class DailyReportAdapter extends BaseQuickAdapter<DailyReport, BaseViewHolder> {
    Activity mContext;

    Calendar calendar = Calendar.getInstance();

    public DailyReportAdapter(Activity mContext, @Nullable List<DailyReport> data) {
        super(R.layout.item_report, data);
        this.mContext = mContext;

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
    }

    @Override
    protected void convert(final BaseViewHolder helper, DailyReport item) {
        helper.setText(R.id.id_child_tv, item.getTitle());

//        UserInfoUtils.getInfoByObjectId(item.getUid(), new UserInfoUtils.IResult() {
//            @Override
//            public void onReslt(User userInfo) {
//                if (userInfo != null) {
//                    helper.setText(R.id.name_child_tv, userInfo.getNickName());
//                }
//            }
//        });

        switch (item.getCheck()) {
            case 0:
                helper.setBackgroundRes(R.id.iv_icon, R.color.themecolor);
                helper.setTextColor(R.id.name_child_tv, mContext.getResources().getColor(R.color.black));
                helper.setTextColor(R.id.id_child_tv, mContext.getResources().getColor(R.color.black));
                break;
            case 1:
                helper.setBackgroundRes(R.id.iv_icon, R.color.text_color_orange);
                helper.setTextColor(R.id.name_child_tv, mContext.getResources().getColor(R.color.text_color_orange));
                helper.setTextColor(R.id.id_child_tv, mContext.getResources().getColor(R.color.text_color_orange));
                break;
            case 2:
                helper.setBackgroundRes(R.id.iv_icon, R.color.text_color_red);
                helper.setTextColor(R.id.name_child_tv, mContext.getResources().getColor(R.color.text_color_red));
                helper.setTextColor(R.id.id_child_tv, mContext.getResources().getColor(R.color.text_color_red));
                break;
        }

        try {
            String time = "";
            if (TextUtils.isEmpty(item.getUpdateTime())) {
                time = item.getCreateTime();
            } else {
                time = item.getUpdateTime();
            }
            helper.setText(R.id.name_child_tv, time.substring(11, 16));
            helper.setVisible(R.id.name_child_tv, true);
        } catch (Exception e) {
        }

    }

}
