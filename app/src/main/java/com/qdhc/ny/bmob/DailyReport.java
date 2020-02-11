package com.qdhc.ny.bmob;

import cn.bmob.v3.BmobObject;

/**
 * 新的日报对象
 *
 * @Author wj
 * @Date 2019/12/14
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class DailyReport extends BmobObject {

    /**
     * 用户ID
     */
    String uid;
    /**
     * 项目ID
     */
    String pid;
    /**
     * 标题
     */
    String title;

    /**
     * 内容
     */
    String content;

    /**
     * 检验结果
     */
    int check = -1;

    /**
     * 更新的进度
     */
    int schedule = 0;

    /**
     * 上传地址
     */
    String address = "";
    /**
     * 街道名
     */
    String street = "";
    /**
     * 区县名字
     */
    String district = "";

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }
}
