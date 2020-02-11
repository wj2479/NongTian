package com.qdhc.ny.bmob;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 项目进度对象
 *
 * @Author wj
 * @Date 2019/11/7
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class ProjSchedule extends BmobObject implements Serializable {
    /**
     * 项目ID
     */
    String pid;
    /**
     * 用户ID
     */
    String uid;
    /**
     * 进度文字描述
     */
    String content;
    /**
     * 更新的进度
     */
    int schedule;

    /**
     * 备注信息
     */
    String remark;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "ProjSchedule{" +
                "pid='" + pid + '\'' +
                ", uid='" + uid + '\'' +
                ", content='" + content + '\'' +
                ", schedule=" + schedule +
                '}';
    }
}
