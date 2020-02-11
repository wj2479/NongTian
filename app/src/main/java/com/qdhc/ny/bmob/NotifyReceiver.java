package com.qdhc.ny.bmob;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * @Author wj
 * @Date 2019/11/22
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class NotifyReceiver extends BmobObject implements Serializable {

    String nid;
    String uid;
    boolean isRead;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "NotifyReceiver{" +
                "nid='" + nid + '\'' +
                ", uid='" + uid + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}
