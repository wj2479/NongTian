package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.bmob.DailyReport;

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
//            public void onReslt(UserInfo userInfo) {
//                if (userInfo != null) {
//                    helper.setText(R.id.name_child_tv, userInfo.getNickName());
//                }
//            }
//        });
        try {
            String dayTime = item.getCreatedAt().substring(0, 10);
            helper.setText(R.id.name_child_tv, dayTime);
            helper.setVisible(R.id.name_child_tv, true);
        } catch (Exception e) {
        }
    }

}
