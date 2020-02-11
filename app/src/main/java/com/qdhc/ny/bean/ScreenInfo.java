package com.qdhc.ny.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 筛选数据传递使用
 * Created by 申健 on 2019/4/5.
 */

public class ScreenInfo implements Parcelable {
    public static final Creator<ScreenInfo> CREATOR = new Creator<ScreenInfo>() {
        @Override
        public ScreenInfo createFromParcel(Parcel source) {
            return new ScreenInfo(source);
        }

        @Override
        public ScreenInfo[] newArray(int size) {
            return new ScreenInfo[size];
        }
    };
    private String startTime;
    private String endTime;
    //管理员id
    private String userId;
    //客户经理id
    private String customerId;
    //揽存号 retain
    private String retainId;
    //支行
    private String subbranch;

    public ScreenInfo() {
    }

    protected ScreenInfo(Parcel in) {
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.userId = in.readString();
        this.customerId = in.readString();
        this.retainId = in.readString();
        this.subbranch = in.readString();
    }

    public String getStartTime() {
        return startTime == null ? "" : startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime == null ? "" : endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerId() {
        return customerId == null ? "" : customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRetainId() {
        return retainId == null ? "" : retainId;
    }

    public void setRetainId(String retainId) {
        this.retainId = retainId;
    }

    public String getSubbranch() {
        return subbranch == null ? "" : subbranch;
    }

    public void setSubbranch(String subbranch) {
        this.subbranch = subbranch;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.userId);
        dest.writeString(this.customerId);
        dest.writeString(this.retainId);
        dest.writeString(this.subbranch);
    }
}
