package com.qdhc.ny.bmob;

import cn.bmob.v3.BmobObject;

/**
 * 日报 周报 月报对象
 *
 * @Author wj
 * @Date 2019/11/7
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class Report extends BmobObject {

    /**
     * 项目ID
     */
    String pid;
    /**
     * 用户ID
     */
    String uid;

    /**
     * 今日工作
     */
    String worktoday;
    /**
     * 文字描述
     */
    String description;
    /**
     * 质量自检
     */
    String check;
    /**
     * 存在问题
     */
    String question;
    /**
     * 解决办法
     */
    String method;
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
    /**
     * 类型   日报 周报 月报
     */
    int type;

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

    public String getWorktoday() {
        return worktoday;
    }

    public void setWorktoday(String worktoday) {
        this.worktoday = worktoday;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Report{" +
                "pid='" + pid + '\'' +
                ", uid='" + uid + '\'' +
                ", worktoday='" + worktoday + '\'' +
                ", description='" + description + '\'' +
                ", check='" + check + '\'' +
                ", question='" + question + '\'' +
                ", method='" + method + '\'' +
                ", address='" + address + '\'' +
                ", street='" + street + '\'' +
                ", district='" + district + '\'' +
                ", type=" + type +
                '}';
    }
}
