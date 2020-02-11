package com.qdhc.ny.bean;

/**
 * Created by 申健 on 2019/3/27.
 */

public class AMapLocation {

    /**
     * location : string
     * locatetime : 0
     * speed : 0
     * direction : 0
     * height : 0
     * accuracy : 0
     */

    private String location;
    private int locatetime;
    private int speed;
    private int direction;
    private int height;
    private int accuracy;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLocatetime() {
        return locatetime;
    }

    public void setLocatetime(int locatetime) {
        this.locatetime = locatetime;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
