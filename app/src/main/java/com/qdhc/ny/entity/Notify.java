package com.qdhc.ny.entity;

import java.io.Serializable;

/**
 * 通知对象
 */
public class Notify implements Serializable {

    static final long serialVersionUID = 17226898355L;

    /**
     * 通知ID ，主键
     */
    private int id;

    /**
     * 创建者ID
     */
    private int uid;

    /**
     * 标题
     */
    private String title = "";

    /**
     * 内容
     */
    private String content = "";

    /**
     * 是否删除
     */
    private boolean isDel = false;

    /**
     * 删除时间
     */
    private String deleteTime = "";

    /**
     * 是否撤销
     */
    private boolean isCancel = false;

    /**
     * 撤销时间
     */
    private String cancelTime = "";

    /**
     * 通知类型
     */
    private int type = 0;

    /**
     * 接收者id，以,分割
     */
    private String receivers = "";

    /**
     * 创建时间
     */
    private String createTime = "";

    /**
     * 更新时间
     */
    private String updateTime = "";

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Notify{" +
                "id=" + id +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", isDel=" + isDel +
                ", deleteTime='" + deleteTime + '\'' +
                ", isCancel=" + isCancel +
                ", cancelTime='" + cancelTime + '\'' +
                ", type=" + type +
                ", receivers='" + receivers + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
