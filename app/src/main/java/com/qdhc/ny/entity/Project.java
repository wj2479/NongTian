package com.qdhc.ny.entity;

import java.io.Serializable;

/**
 * 项目对象
 */
public class Project implements Serializable {

    static final long serialVersionUID = 17786898355L;

    /**
     * 用户ID ，主键
     */
    private int id;

    /**
     * 项目名称
     */
    private String name = "";

    /**
     * 项目介绍
     */
    private String info = "";

    /**
     * 项目进度
     */
    private float process;

    /**
     * 项目创建者Id
     */
    private int creater;

    /**
     * 项目所属区域ID
     */
    private Area area;

    /**
     * 上级项目的ID  根项目的id为-1
     */
    private int parentId;

    /**
     * 项目标签，以,分割
     */
    private String tags = "";

    /**
     * 项目的级别
     */
    private int level = 0;

    /**
     * 项目的管理者
     */
    private User manager = null;

    /**
     * 创建时间
     */
    private String createTime = "";

    /**
     * 更新时间
     */
    private String updateTime = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getProcess() {
        return process;
    }

    public void setProcess(float process) {
        this.process = process;
    }

    public int getCreater() {
        return creater;
    }

    public void setCreater(int creater) {
        this.creater = creater;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", process=" + process +
                ", creater=" + creater +
                ", area=" + area +
                ", parentId=" + parentId +
                ", tags='" + tags + '\'' +
                ", level=" + level +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
