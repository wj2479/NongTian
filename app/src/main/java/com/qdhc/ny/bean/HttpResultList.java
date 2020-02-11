package com.qdhc.ny.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有实体的一个基类，data可以为任何数据类型。
 * 数据格式带数组
 * Created by 申健 on 2018/8/24.
 */

public class HttpResultList<T> {

    private int state;
    private boolean success;
    private String message;
    private List<T> data;

    public boolean isSuccess() {
        return getCode()==1;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<T> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCode() {
        return state;
    }

    public void setCode(int code) {
        this.state = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

