package com.qdhc.ny.entity;

import java.io.Serializable;

/**
 * 通知阅读对象
 */
public class NotifyReceiver extends Notify implements Serializable {

    /**
     * 是否已读
     */
    private boolean read = false;

    /**
     * 阅读时间
     */
    private String readTime = "";

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    @Override
    public String toString() {
        return "NotifyReceiver{" +
                "read=" + read +
                ", readTime='" + readTime + '\'' +
                '}';
    }
}
