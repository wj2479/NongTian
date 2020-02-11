package com.qdhc.ny.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vondear.rxtool.RxTimeTool;
import com.vondear.rxui.R;
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

public class RxDialogWheelYearMonthDay extends RxDialog {
    private WheelView mYearView;
    private WheelView mMonthView;
    private WheelView mDayView;
    private int curYear;
    private int curMonth;
    private int curDay;
    private TextView mTvSure;
    private TextView mTvCancle;
    private CheckBox mCheckBoxDay;
    private Calendar mCalendar;
    private LinearLayout llType;
    private String mMonths[] = new String[]{"01", "02", "03",
            "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String curMonths[] = new String[]{};//当前月份
    private String mDays[] = new String[]{"01", "02", "03", "04", "05", "06", "07",
            "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String curDays[] = new String[]{};//当前天数
    private int beginYear = 0;
    private int endYear = 0;
    private int divideYear = endYear - beginYear;

    public RxDialogWheelYearMonthDay(Context mContext) {
        super(mContext);
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        build();
    }

    public RxDialogWheelYearMonthDay(Context mContext, int beginYear) {
        super(mContext);
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.beginYear = beginYear;
        build();
    }

    public RxDialogWheelYearMonthDay(Context mContext, int beginYear, int endYear) {
        super(mContext);
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.beginYear = beginYear;
        this.endYear = endYear;
        build();
    }

    public RxDialogWheelYearMonthDay(Context mContext, TextView tvTime) {
        super(mContext);
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        build();
        tvTime.setText(curYear + "年" + mMonths[curMonth] + "月");
    }

    private void build() {
        mCalendar = Calendar.getInstance();
        final View dialogView1 = LayoutInflater.from(mContext).inflate(R.layout.dialog_year_month_day, null);
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
                    mDayView.setViewAdapter(new DateNumericAdapter(mContext, 1, mCalendar.get(Calendar.DAY_OF_MONTH), 0));
                    mDayView.setCurrentItem(0 , true);
                } else {
                    mMonthView.setViewAdapter(new DateArrayAdapter(mContext, mMonths, 0));
                    int maxDays = RxTimeTool.getDaysByYearMonth(beginYear + year.getCurrentItem(), 0);
                    mDayView.setViewAdapter(new DateNumericAdapter(mContext, 1, maxDays, 0));
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
                if (Integer.parseInt(getSelectorMonth()) == curMonth+1&&getSelectorYear() == curYear) {//当前年 重置月份和天
                    mDayView.setViewAdapter(new DateNumericAdapter(mContext, 1, mCalendar.get(Calendar.DAY_OF_MONTH), 0));
                    mDayView.setCurrentItem(0 , true);
                } else {
                    int maxDays = RxTimeTool.getDaysByYearMonth(beginYear + mYearView.getCurrentItem(), 0);
                    mDayView.setViewAdapter(new DateNumericAdapter(mContext, 1, maxDays, 0));
                }
            }
        });


        //mDayView
        mDayView = dialogView1.findViewById(R.id.wheelView_day);
        curDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        curDays = Arrays.copyOfRange(getDays(), 0, curDay);
        mDayView.setViewAdapter(new DateNumericAdapter(mContext, 1, mCalendar.get(Calendar.DAY_OF_MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)));
        mDayView.setCurrentItem(curDay - 1);
        mDayView.setBackgroundResource(R.drawable.transparent_bg);
        mDayView.setWheelBackground(R.drawable.transparent_bg);
        mDayView.setWheelForeground(R.drawable.wheel_val_holo);
        mDayView.setShadowColor(0xFFDADCDB, 0x88DADCDB, 0x00DADCDB);

        mTvSure = dialogView1.findViewById(R.id.tv_sure);
        mTvCancle = dialogView1.findViewById(R.id.tv_cancel);
        llType = dialogView1.findViewById(R.id.ll_month_type);

        mCheckBoxDay = dialogView1.findViewById(R.id.checkBox_day);
        llType.setVisibility(View.GONE);
        mCheckBoxDay.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDayView.setVisibility(View.VISIBLE);
                } else {
                    mDayView.setVisibility(View.GONE);
                }
            }
        });

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

    public void setMonthType(Boolean isTrue) {
        if (isTrue) {
            llType.setVisibility(View.INVISIBLE);
        } else {
            llType.setVisibility(View.VISIBLE);
        }
        mCheckBoxDay.setChecked(!isTrue);

    }

    public int getCurDay() {
        return curDay;
    }

    public CheckBox getCheckBoxDay() {
        return mCheckBoxDay;
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

    public String getSelectorDay() {
        return getDays()[getDayView().getCurrentItem()];
    }

    private String[] getDays() {
        return mDays;
    }

    public WheelView getDayView() {
        return mDayView;
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