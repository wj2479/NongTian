package com.qdhc.ny.bean;

/**
 * Created by 申健 on 2019/4/13.
 */

public class ATableTitle {
    //业绩头部数据
    private String title;
    private int state;//根据状态显示按钮 0 没有图片  大于0 绿色  小于0 红色

    public ATableTitle() {
    }

    public ATableTitle(String title, int state) {
        this.title = title;
        this.state = state;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
