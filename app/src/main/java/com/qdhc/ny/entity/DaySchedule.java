package com.qdhc.ny.entity;

import java.io.Serializable;

/**
 * 日报汇总对象，记录每天日报的结果
 */
public class DaySchedule implements Serializable {

    /**
     * 日期
     */
    private String date = "";

    /**
     * 当前日期的最新进度
     */
    private int schedule = 0;

    /**
     * 最后一条媒体数据
     */
    private Media lastMedia = null;

    /**
     * 更新时间
     */
    private String updateTime = "";

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }

    public Media getLastMedia() {
        return lastMedia;
    }

    public void setLastMedia(Media lastMedia) {
        this.lastMedia = lastMedia;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "DaySchedule{" +
                "date='" + date + '\'' +
                ", schedule=" + schedule +
                ", lastMedia=" + lastMedia +
                '}';
    }
}
