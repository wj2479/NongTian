package com.qdhc.ny.bmob;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 通知
 *
 * @Author wj
 * @Date 2019/11/7
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class Notify extends BmobObject implements Serializable {

    /**
     * 内容
     */
    String content;
    /**
     * 发布者
     */
    String publish;

    /**
     * 是否已读
     */
    boolean isRead;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
