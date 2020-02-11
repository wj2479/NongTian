package com.qdhc.ny.bean;

/**
 * 业绩excel title标题
 * Created by 申健 on 2019/4/6.
 */

public class SPTitleInfo {
    private String title;
    // 0 不显示  1 降序 2 升序
    private int code;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
