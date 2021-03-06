package com.qdhc.ny.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qdhc.ny.R;
import com.qdhc.ny.entity.DailyReport;

import java.util.Calendar;
import java.util.List;

/**
 * 通知列表
 */

public class ReportAdapter extends BaseQuickAdapter<DailyReport, BaseViewHolder> {
    Activity mContext;

    Calendar calendar = Calendar.getInstance();

    public ReportAdapter(Activity mContext, @Nullable List<DailyReport> data) {
        super(R.layout.item_report, data);
        this.mContext = mContext;

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
    }

    @Override
    protected void convert(final BaseViewHolder helper, DailyReport item) {

    }

}
