package com.qdhc.ny.common;

import com.amap.api.location.AMapLocation;
import com.qdhc.ny.bean.UserTreeNode;
import com.qdhc.ny.bmob.Notice;
import com.qdhc.ny.bmob.Project;
import com.qdhc.ny.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wj
 * @Date 2019/11/13
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class ProjectData {

    /**
     * 当前用户
     */
    private User userInfo = null;

    /**
     * 当前工程的列表
     */
    private List<Project> projects = new ArrayList<>();

    /**
     * 当前定位的位置
     */
    private AMapLocation location = null;

    /**
     * 公告列表
     */
    private List<Notice> notices = new ArrayList<>();

    /**
     * 地区，人员总节点
     */
    private UserTreeNode rootNode = new UserTreeNode();

    private static ProjectData mPorjectData = null;

    private ProjectData() {
    }

    public static ProjectData getInstance() {
        if (mPorjectData == null) {
            synchronized (ProjectData.class) {
                if (mPorjectData == null)
                    mPorjectData = new ProjectData();
            }
        }
        return mPorjectData;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public AMapLocation getLocation() {
        return location;
    }

    public void setLocation(AMapLocation location) {
        this.location = location;
    }

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

    public UserTreeNode getRootNode() {
        return rootNode;
    }





    int cut = 0;

    /**
     * 释放内存资源
     */
    public void release() {
        cut = 0;
        userInfo = null;
        projects.clear();
        notices.clear();
        rootNode.reset();
    }

}
