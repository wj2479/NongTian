package com.qdhc.ny.eventbus;

import com.qdhc.ny.bean.AMapLocation;

/**
 * @Author wj
 * @Date 2019/11/13
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class LocationEvent {

    AMapLocation location;

    public LocationEvent(AMapLocation location) {
        this.location = location;
    }

    public AMapLocation getLocation() {
        return location;
    }

    public void setLocation(AMapLocation location) {
        this.location = location;
    }
}
