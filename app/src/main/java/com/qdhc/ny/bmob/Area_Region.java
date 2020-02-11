package com.qdhc.ny.bmob;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * @Author wj
 * @Date 2020/1/2
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class Area_Region extends BmobObject implements Serializable {

    /**
     * 名称
     */
    String name = "";

    /**
     * 简称
     */
    String short_name = "";

    /**
     * 编号
     */
    String region_code = "";

    /**
     * 父ID
     */
    String parent_id = "";

    /**
     * 地区等级
     */
    int region_level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getRegion_code() {
        return region_code;
    }

    public void setRegion_code(String region_code) {
        this.region_code = region_code;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public int getRegion_level() {
        return region_level;
    }

    public void setRegion_level(int region_level) {
        this.region_level = region_level;
    }

    @Override
    public String toString() {
        return "Area_Region{" +
                "name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                ", region_code='" + region_code + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", region_level=" + region_level +
                '}';
    }
}
