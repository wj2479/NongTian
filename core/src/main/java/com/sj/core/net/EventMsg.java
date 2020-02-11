package com.sj.core.net;

/**
 * Created by 申健 on 2019/4/6.
 */

public class EventMsg {
    private int code;
    private String mMessage;
    private Object mObject;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return mMessage == null ? "" : mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Object getObject() {
        return mObject;
    }

    public void setObject(Object object) {
        mObject = object;
    }
}
