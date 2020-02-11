package com.qdhc.ny.view;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * @Author wj
 * @Date 2020/1/6
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class XAxisValueFormatter implements IAxisValueFormatter {
    String[] datas;

    public XAxisValueFormatter( String[] datas) {
        this.datas = datas;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int position = (int) value;
        if (datas != null && datas.length > position) {
            return datas[position];
        }
        return "未知";
    }

}