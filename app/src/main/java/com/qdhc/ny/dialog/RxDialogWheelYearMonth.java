package com.qdhc.ny.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qdhc.ny.R;
import com.vondear.rxui.view.dialog.wheel.DateArrayAdapter;
import com.vondear.rxui.view.dialog.wheel.NumericWheelAdapter;
import com.vondear.rxui.view.dialog.wheel.OnWheelChangedListener;
import com.vondear.rxui.view.dialog.wheel.WheelView;

import java.util.Arrays;
import java.util.Calendar;

/**
 * 选择之前的时间
 * Created by 申健 on 2019/3/23.
 */
public class RxDialogWheelYearMonth extends RxDialog {
    private WheelView mYearView;
    private WheelView mMonthView;
    private int curYear;
    private int curMonth;
    private TextView mTvSure;
    private TextView mTvCancle;
    private Calendar mCalendar;
    private String mMonths[] = new String[]{"01", "02", "03",
            "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    private String curMonths[] = new String[]{};//当前月份
    private int beginYear = 0;
    private int endYear = 0;
    private int divideYear = endYear - beginYear;

    public RxDialogWheelYearMonth(Context mContext) {
        super(mContext);
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        build();
    }

    public RxDialogWheelYearMonth(Context mContext, int beginYear) {
        super(mContext);
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.beginYear = beginYear;
        build();
    }

    public RxDialogWheelYearMonth(Context mContext, int beginYear, int endYear) {
        super(mContext);
        this.mContext = mContext;
        this.beginYear = beginYear;
        this.endYear = endYear;
        build();
    }

    public RxDialogWheelYearMonth(Context mContext, TextView tvTime) {
        super(mContext);
        this.mContext = mContext;
        build();
        tvTime.setText(curYear + "年" + mMonths[curMonth] + "月");
    }

    private void build() {
        mCalendar = Calendar.getInstance();
        final View dialogView1 = LayoutInflater.from(mContext).inflate(R.layout.dialog_year_month, null);
        curYear = mCalendar.get(Calendar.YEAR);
        if (beginYear == 0) {
            beginYear = curYear - 5;
        }
        if (endYear == 0) {
            endYear = curYear;
        }
        if (beginYear > endYear) {
            endYear = beginYear;
        }
        //mYearView
        mYearView = dialogView1.findViewById(R.id.wheelView_year);
        mYearView.setBackgroundResource(R.drawable.transparent_bg);
        mYearView.setWheelBackground(R.drawable.transparent_bg);
        mYearView.setWheelForeground(R.drawable.wheel_val_holo);
        mYearView.setShadowColor(0xFFDADCDB, 0x88DADCDB, 0x00DADCDB);
        mYearView.setViewAdapter(new DateNumericAdapter(mContext, beginYear, endYear, endYear - beginYear));
        mYearView.setCurrentItem(endYear - beginYear);
        mYearView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView year, int oldValue, int newValue) {
                if (getSelectorYear() == curYear) {//当前年 重置月份和天
                    mMonthView.setViewAdapter(new DateArrayAdapter(mContext, curMonths, 0));
                    mMonthView.setCurrentItem(0, true);
                } else {
                    mMonthView.setViewAdapter(new DateArrayAdapter(mContext, mMonths, 0));
                }
            }
        });


        // mMonthView
        mMonthView = dialogView1
                .findViewById(R.id.wheelView_month);
        mMonthView.setBackgroundResource(R.drawable.transparent_bg);
        mMonthView.setWheelBackground(R.drawable.transparent_bg);
        mMonthView.setWheelForeground(R.drawable.wheel_val_holo);
        mMonthView.setShadowColor(0xFFDADCDB, 0x88DADCDB, 0x00DADCDB);
        curMonth = mCalendar.get(Calendar.MONTH);
        curMonths = Arrays.copyOfRange(getMonths(), 0, curMonth + 1);
        mMonthView.setViewAdapter(new DateArrayAdapter(mContext, curMonths, curMonth));
        mMonthView.setCurrentItem(curMonth);
        mMonthView.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheelView, int i, int i1) {
            }
        });


        mTvSure = dialogView1.findViewById(R.id.tv_sure);
        mTvCancle = dialogView1.findViewById(R.id.tv_cancel);

        getLayoutParams().gravity = Gravity.CENTER;
        setContentView(dialogView1);
    }

    public int getSelectorYear() {
        return beginYear + getYearView().getCurrentItem();
    }

    private int getCurMonth() {
        return curMonth;
    }

    public WheelView getYearView() {
        return mYearView;
    }

    public int getBeginYear() {
        return beginYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public int getDivideYear() {
        return divideYear;
    }

    private int getCurYear() {
        return curYear;
    }

    public TextView getSureView() {
        return mTvSure;
    }

    public TextView getCancleView() {
        return mTvCancle;
    }

    public String getSelectorMonth() {
        return getMonths()[getMonthView().getCurrentItem()];
    }

    private String[] getMonths() {
        return mMonths;
    }

    public WheelView getMonthView() {
        return mMonthView;
    }

    /**
     * Adapter for numeric wheels. Highlights the current value.
     */
    private class DateNumericAdapter extends NumericWheelAdapter {
        // Index of current item
        int currentItem;
        // Index of item to be highlighted
        int currentValue;

        /**
         * Constructor
         */
        public DateNumericAdapter(Context mContext, int minValue, int maxValue, int current) {
            super(mContext, minValue, maxValue);
            this.currentValue = current;
            setTextSize(16);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            currentItem = index;
            return super.getItem(index, cachedView, parent);
        }

        @Override
        protected void configureTextView(TextView view) {
            super.configureTextView(view);
            /*if (currentItem == currentValue) {
                view.setTextColor(0xFF0000F0);
			}*/
            view.setTypeface(Typeface.SANS_SERIF);
        }
    }
}