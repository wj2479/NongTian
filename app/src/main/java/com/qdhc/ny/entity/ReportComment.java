package com.qdhc.ny.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.qdhc.ny.common.ProjectData;

import java.util.ArrayList;
import java.util.List;

/**
 * 日报评论对象
 */
public class ReportComment implements MultiItemEntity {

    public static final int TYPE_LEFT = 0;
    public static final int TYPE_RIGHT = 1;

    /**
     * 日报的ID
     */
    private int id;

    /**
     * 日报ID
     */
    private int rid;

    /**
     * 用户ID
     */
    private int sender;

    /**
     * 评论内容
     */
    private String content = "";

    /**
     * 创建时间
     */
    private String createTime = "";

    /**
     * 评论的多媒体信息
     */
    private List<Media> mediaList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<Media> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<Media> mediaList) {
        this.mediaList = mediaList;
    }

    @Override
    public int getItemType() {
        if (ProjectData.getInstance().getUserInfo().getId() == sender) {
            return TYPE_RIGHT;
        }
        return TYPE_LEFT;
    }
}
