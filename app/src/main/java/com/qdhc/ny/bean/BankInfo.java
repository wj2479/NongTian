package com.qdhc.ny.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 申健 on 2019/4/16.
 */

public class BankInfo {

    private List<String> fenhang;
    private List<String> zhihang;

    public List<String> getZhihang() {
        if (zhihang == null) {
            return new ArrayList<String>();
        }
        return zhihang;
    }

    public void setZhihang(List<String> zhihang) {
        this.zhihang = zhihang;
    }

    public List<String> getFenhang() {
        return fenhang;
    }

    public void setFenhang(List<String> fenhang) {
        this.fenhang = fenhang;
    }

}
