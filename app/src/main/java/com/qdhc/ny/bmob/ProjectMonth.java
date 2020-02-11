package com.qdhc.ny.bmob;

import cn.bmob.v3.BmobObject;

/**
 * 工程月度目标
 *
 * @Author wj
 * @Date 2019/12/13
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class ProjectMonth extends BmobObject {

    /**
     * 工程ID
     */
    String pid;
    /**
     * 监理ID
     */
    String uid;
    /**
     * 年份
     */
    int year;
    /**
     * 月份
     */
    int month;
    /**
     * 目标进度  1-100
     */
    int target;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
