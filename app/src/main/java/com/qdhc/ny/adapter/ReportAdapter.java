package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.bmob.Report;
import com.qdhc.ny.bmob.UserInfo;
import com.qdhc.ny.common.Constant;
import com.qdhc.ny.utils.UserInfoUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.bigkoo.pickerview.view.WheelTime.dateFormat;

/**
 * 通知列表
 */

public class ReportAdapter extends BaseQuickAdapter<Report, BaseViewHolder> {
    Activity mContext;

    Calendar calendar = Calendar.getInstance();

    public ReportAdapter(Activity mContext, @Nullable List<Report> data) {
        super(R.layout.item_report, data);
        this.mContext = mContext;

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
    }

    @Override
    protected void convert(final BaseViewHolder helper, Report item) {
        Date date = null;
        switch (item.getType()) {
            case Constant.REPORT_TYPE_DAY:
                String dayTime = item.getCreatedAt().substring(0, 10);
                helper.setText(R.id.id_child_tv, "日报 :  " + dayTime);
                break;
            case Constant.REPORT_TYPE_WEEK:
                try {
                    date = dateFormat.parse(item.getCreatedAt());
                } catch (ParseException e) {
                }
                calendar.setTime(date);

                helper.setText(R.id.id_child_tv, "周报 :  " + (calendar.get(Calendar.MONTH) + 1) + "月  第" + calendar.get(Calendar.WEEK_OF_MONTH) + "周");
                break;
            case Constant.REPORT_TYPE_MONTH:

                try {
                    date = dateFormat.parse(item.getCreatedAt());
                } catch (ParseException e) {
                }
                calendar.setTime(date);
                helper.setText(R.id.id_child_tv, "月报 :  " + calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
                break;
        }

        UserInfoUtils.getInfoByObjectId(item.getUid(), new UserInfoUtils.IResult() {
            @Override
            public void onReslt(UserInfo userInfo) {
                if (userInfo != null) {
                    helper.setText(R.id.name_child_tv, userInfo.getNickName());
                }
            }
        });

    }

}
