package com.qdhc.ny.bean;

/**
 * 所有实体的一个基类，data可以为任何数据类型。
 * 数据格式带实体
 * Created by 申健 on 2018/8/24.
 */

public class HttpResult<T> {

    private int state;
    private String message;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return getState()==1;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

