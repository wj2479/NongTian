package com.qdhc.ny.entity;

/**
 * APP更新对象
 */
public class AppUpdate {
    /**
     * 主键
     */
    int id;
    /**
     * 版本号
     */
    int versionCode;
    /**
     * 版本名称
     */
    String versionName = "";
    /**
     * 更新内容
     */
    String content = "";
    /**
     * 文件下载地址
     */
    String url = "";
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

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
