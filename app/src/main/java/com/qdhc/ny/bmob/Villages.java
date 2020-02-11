package com.qdhc.ny.bmob;

import cn.bmob.v3.BmobObject;

/**
 * 村镇
 *
 * @Author wj
 * @Date 2019/11/19
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class Villages extends BmobObject {

    int areaId;

    String name;

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Villages{" +
                "areaId=" + areaId +
                ", name='" + name + '\'' +
                '}';
    }
}
