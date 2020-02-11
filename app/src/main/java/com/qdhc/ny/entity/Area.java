package com.qdhc.ny.entity;

import java.io.Serializable;

/**
 * 行政区域信息
 */
public class Area implements Serializable {

    static final long serialVersionUID = 177864448355L;

    /**
     * 区域ID
     */
    private int id;

    /**
     * 行政级别
     */
    private int regionLevel;

    /**
     * 上级地域ID
     */
    private int parentId;

    /**
     * 上级区域对象
     */
    private Area parent;

    /**
     * 地域名称
     */
    private String name;

    /**
     * 行政编码
     */
    private String regionCode;

    /**
     * 简称
     */
    private String shortName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(int regionLevel) {
        this.regionLevel = regionLevel;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Area getParent() {
        return parent;
    }

    public void setParent(Area parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", regionLevel=" + regionLevel +
                ", parentId=" + parentId +
                ", parent=" + parent +
                ", name='" + name + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }
}
