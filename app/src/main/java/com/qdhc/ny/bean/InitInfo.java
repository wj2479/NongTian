package com.qdhc.ny.bean;

/**
 * 初始化信息
 * Created by 申健 on 2019/3/31.
 */

public class InitInfo {

    /**
     * workStartTime : 08:00
     * workEndTime : 17:00
     * radius : 350
     * appVersion : 1.0.0
     */

    private String workStartTime;
    private String workEndTime;
    private int radius;
    private String appVersion;
    private String appUrl;

    public String getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(String workStartTime) {
        this.workStartTime = workStartTime;
    }

    public String getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(String workEndTime) {
        this.workEndTime = workEndTime;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppUrl() {
        return appUrl == null ? "" : appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
}
