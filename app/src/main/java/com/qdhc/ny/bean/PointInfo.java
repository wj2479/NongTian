package com.qdhc.ny.bean;

/**
 * Created by 申健 on 2019/3/28.
 */

public class PointInfo {

    /**
     * userid : 343
     * location : 116.478928,39.997761
     * lon : 116.478928
     * lat : 39.997761
     * locatetime : 1553763958147
     * speed : 19
     * accuracy : 20
     * direction : 0
     * height : 39
     * Milliseconds : 1
     * locateDateTime : 2018-06-13T08:54:24.457
     * AddDate : 2019-03-27T14:19:00.43
     */

    private int userid;
    private String location;
    private double lon;
    private double lat;
    private long locatetime;
    private float speed;
    private float accuracy;
    private float direction;
    private float height;
    private float Milliseconds;
    private String locateDateTime;
    private String AddDate;
    private String address;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getLocation() {
        return location == null ? "" : location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public long getLocatetime() {
        return locatetime;
    }

    public void setLocatetime(long locatetime) {
        this.locatetime = locatetime;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getMilliseconds() {
        return Milliseconds;
    }

    public void setMilliseconds(float milliseconds) {
        Milliseconds = milliseconds;
    }

    public String getLocateDateTime() {
        return locateDateTime == null ? "" : locateDateTime;
    }

    public void setLocateDateTime(String locateDateTime) {
        this.locateDateTime = locateDateTime;
    }

    public String getAddDate() {
        if (AddDate==null) return  "";
        String dateTime= AddDate.split("\\.")[0].replace("T"," ");
        return   dateTime.substring(0,dateTime.length()-3);
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "PointInfo{" +
                "userid=" + userid +
                ", location='" + location + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                ", locatetime=" + locatetime +
                ", speed=" + speed +
                ", accuracy=" + accuracy +
                ", direction=" + direction +
                ", height=" + height +
                ", Milliseconds=" + Milliseconds +
                ", locateDateTime='" + locateDateTime + '\'' +
                ", AddDate='" + AddDate + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
