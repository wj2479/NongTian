package com.qdhc.ny.entity;

/**
 * 路径记录
 */
public class Track {

    /**
     * ID ，主键
     */
    private int id;
    /**
     * 用户ID
     */
    private int uid;
    /**
     * 纬度
     */
    private double lat;
    /**
     * 经度
     */
    private double lng;
    /**
     * 速度
     */
    private float speed;
    /**
     * 设备类型
     */
    private String deviceModel;
    /**
     * 设备版本
     */
    private String deviceVersion;
    /**
     * 设备厂商
     */
    private String deviceFacturer;
    /**
     * 定位时间
     */
    private long locationTime;
    /**
     * 定位精度
     */
    private float accuracy;
    /**
     * 定位类型
     */
    private String locationType;
    /**
     * 地址信息
     */
    private String address;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 运动方向
     */
    private float direction;

    /**
     * 创建时间
     */
    private String createTime = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
