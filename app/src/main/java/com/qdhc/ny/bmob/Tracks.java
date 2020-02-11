package com.qdhc.ny.bmob;

import cn.bmob.v3.BmobObject;

/**
 * 轨迹点
 *
 * @Author wj
 * @Date 2019/11/7
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class Tracks extends BmobObject {
    /**
     * 用户ID
     */
    String uid;
    /**
     * 纬度
     */
    double lat;
    /**
     * 经度
     */
    double lng;

    /**
     * 速度
     */
    float speed;

    /**
     * 设备类型
     */
    String deviceModel;

    /**
     * 设备版本
     */
    String deviceVersion;

    /**
     * 设备厂商
     */
    String deviceFacturer;

    /**
     * 定位时间
     */
    long locationTime;

    /**
     * 定位精度
     */
    float accuracy;

    /**
     * 定位类型
     */
    String locationType;

    /**
     * 备注信息
     */
    String remark;

    /**
     * 运动方向
     */
    float direction;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getDeviceFacturer() {
        return deviceFacturer;
    }

    public void setDeviceFacturer(String deviceFacturer) {
        this.deviceFacturer = deviceFacturer;
    }

    public long getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(long locationTime) {
        this.locationTime = locationTime;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Tracks{" +
                "uid='" + uid + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", speed=" + speed +
                ", deviceModel='" + deviceModel + '\'' +
                ", deviceVersion='" + deviceVersion + '\'' +
                ", deviceFacturer='" + deviceFacturer + '\'' +
                ", locationTime=" + locationTime +
                ", accuracy=" + accuracy +
                ", locationType='" + locationType + '\'' +
                ", remark='" + remark + '\'' +
                ", direction=" + direction +
                '}';
    }
}
