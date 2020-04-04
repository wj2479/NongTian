package com.qdhc.ny.dialog;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.necer.calendar.BaseCalendar;
import com.necer.calendar.WeekCalendar;
import com.necer.listener.OnCalendarChangedListener;
import com.qdhc.ny.R;

import org.joda.time.LocalDate;

import java.util.Calendar;
import java.util.List;

/**
 * 日期选择的对话框
 */
public class RxCalendarWeekDialog extends RxDialog {
    private Calendar mCalendar;
    private TextView mTvSure;
    private TextView mTvCancle;
    private TextView mTvResult;

    /**
     * 当前选中的年份
     */
    private int year;
    /**
     * 当前选中的月份
     */
    private int month;
    /**
     * 当前是全年的第几周
     */
    private int weekofYear;
    /**
     * 当前是本月的第几周
     */
    private int weekOfMonth;
    /**
     * 选中的起始日期
     */
    private String startSelectedDate;
    /**
     * 选中的结束日期
     */
    private String endSelectedDate;

    private WeekCalendar weekCalendar;

    public RxCalendarWeekDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        build();
    }

    public RxCalendarWeekDialog(Context context) {
        super(context);
        this.mContext = context;
        build();
    }

    private void build() {
        mCalendar = Calendar.getInstance();
        final View dialogView1 = LayoutInflater.from(mContext).inflate(R.layout.dialog_calendar, null);

        mTvSure = dialogView1.findViewById(R.id.tv_sure);
        mTvCancle = dialogView1.findViewById(R.id.tv_cancel);
        mTvResult = dialogView1.findViewById(R.id.tv_result);

        weekCalendar = dialogView1.findViewById(R.id.weekCalendar);
        weekCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate) {
                Log.e("TAG", year + "年" + month + "月" + "   当前页面选中 " + localDate);
                RxCalendarWeekDialog.this.year = year;
                RxCalendarWeekDialog.this.month = month;
                mCalendar.set(localDate.getYear(), localDate.getMonthOfYear() - 1, localDate.getDayOfMonth());
                weekOfMonth = mCalendar.get(Calendar.WEEK_OF_MONTH);
                weekofYear = localDate.getWeekOfWeekyear();
                List<LocalDate> currectDateList = weekCalendar.getCurrectDateList();
                startSelectedDate = currectDateList.get(0).toString();
                endSelectedDate = currectDateList.get(currectDateList.size() - 1).toString();

                mTvResult.setText(year + "年" + month + "月  第" + weekOfMonth + "周");
            }
        });

        getLayoutParams().gravity = Gravity.CENTER;
        setContentView(dialogView1);
    }


    public TextView getSureView() {
        return mTvSure;
    }

    public TextView getCancleView() {
        return mTvCancle;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getWeekofYear() {
        return weekofYear;
    }

    public int getWeekOfMonth() {
        return weekOfMonth;
    }

    public String getStartSelectedDate() {
        return startSelectedDate;
    }

    public String getEndSelectedDate() {
        return endSelectedDate;
    }

    /**
     * 设置起止时间
     *
     * @param startFormatDate
     * @param endFormatDate
     */
    public void setDateInterval(String startFormatDate, String endFormatDate, String formatInitializeDate) {
        weekCalendar.setDateInterval(startFormatDate, endFormatDate, formatInitializeDate);
    }

}
