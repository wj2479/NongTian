package com.qdhc.ny.entity;

import java.io.Serializable;

public class Media implements Serializable {

    static final long serialVersionUID = 1772235575L;

    /**
     * 路径
     */
    private String url = "";
    /**
     * 文件名字
     */
    private String name = "";
    /**
     * md5校验码
     */
    private String md5 = "";
    /**
     * 文件类型
     */
    private String type = "";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
