package com.qdhc.ny.bmob;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 标段/项目
 *
 * @Author wj
 * @Date 2019/11/7
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class Project extends BmobObject implements Serializable {

    /**
     * 项目名
     */
    String name;
    /**
     * 项目介绍
     */
    String introduce;

    /**
     * 监理负责人ID
     */
    private String manager;

    /**
     * 项目标签
     */
    String tags;

    /**
     * 所属区域
     * @deprecated 1.1.5开始弃用
     */
    int area;

    /**
     * 所属村落ID
     */
    String village;

    /**
     * 所属区县
     */
    private String county = "";

    /**
     * 项目总进度
     */
    int schedule = 0;

    /**
     * 所属城市
     */
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                ", manager='" + manager + '\'' +
                ", tags='" + tags + '\'' +
                ", area=" + area +
                ", village='" + village + '\'' +
                ", schedule=" + schedule +
                ", city=" + city +
                '}';
    }
}
