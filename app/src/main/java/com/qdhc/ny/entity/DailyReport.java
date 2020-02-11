package com.qdhc.ny.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目日报 进度
 */
public class DailyReport implements Serializable {

    static final long serialVersionUID = 17785575L;

    /**
     * 日报的ID
     */
    private int id;

    /**
     * 项目ID
     */
    private int pid;

    /**
     * 用户ID
     */
    private int uid;

    /**
     * 日报标题
     */
    private String title = "";

    /**
     * 日报内容
     */
    private String content = "";

    /**
     * 检查结果
     */
    private int check;

    /**
     * 更新进度
     */
    private int schedule;

    /**
     * 所在地区
     */
    private String district = "";

    /**
     * 上传经纬度，中间使用,分割
     */
    private String lnglat = "";

    /**
     * 上报地址
     */
    private String address = "";

    /**
     * 日报的多媒体信息
     */
    private List<Media> mediaList = new ArrayList<>();

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

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
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

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public int getSchedule() {
        return schedule;
    }

    public void setSchedule(int schedule) {
        this.schedule = schedule;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLnglat() {
        return lnglat;
    }

    public void setLnglat(String lnglat) {
        this.lnglat = lnglat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<Media> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<Media> mediaList) {
        this.mediaList = mediaList;
    }

    @Override
    public String toString() {
        return "DailyReport{" +
                "id=" + id +
                ", pid=" + pid +
                ", uid=" + uid +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", check=" + check +
                ", schedule=" + schedule +
                ", district='" + district + '\'' +
                ", lnglat='" + lnglat + '\'' +
                ", address='" + address + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
