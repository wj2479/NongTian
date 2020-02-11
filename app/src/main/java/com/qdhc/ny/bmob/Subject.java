package com.qdhc.ny.bmob;

import cn.bmob.v3.BmobObject;

/**
 * 项目对象
 *
 * @Author wj
 * @Date 2020/2/1
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class Subject extends BmobObject {
    /**
     * 父项目的ID
     */
    private String parentId = "";
    /**
     * 项目名称
     */
    private String name = "";

    /**
     * 项目进度
     */
    private int process = 0;

    /**
     * 项目地区
     */
    private String region = "";

    /**
     * 项目简介
     */
    private String info = "";
    /**
     * 项目标签  ,分割
     */
    private String tags = "";

    /**
     * 项目创建者ID
     */
    private String creater = "";

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }
}
