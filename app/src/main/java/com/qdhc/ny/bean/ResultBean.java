package com.qdhc.ny.bean;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 申健 on 2018/8/26.
 */

public class ResultBean<T> {
    /**
     * state : 1
     * message : 获取数据成功
     * data : [{"Id":1,"LC_ID":1,"Name":"小类1","ImgUrl":"/Content/images/ca2.jpg","Remark":null,"DisplayOrder":1}]
     */

    private List<T> data;
    private int state;
    private String message;

    public static ResultBean fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResultBean.class, clazz);
        return gson.fromJson(json, objectType);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getRawType() {
                return raw;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return getState() == 1;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String toJson(Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResultBean.class, clazz);
        return gson.toJson(this, objectType);
    }

}
