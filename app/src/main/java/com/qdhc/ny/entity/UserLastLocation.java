package com.qdhc.ny.entity;

/**
 * 用户指定时间最后一次定位对象
 */
public class UserLastLocation {
    /**
     * 用户ID ，主键
     */
    private int uid;

    private String nickName = "";

    private double lat;

    private double lng;

    private String createTime = "";

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
