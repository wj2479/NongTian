package com.qdhc.ny.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户区域节点
 *
 * @Author wj
 * @Date 2020/1/2
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class UserAreaTreeNode {

    /**
     * 区域对象
     */
    private Area area;
    /**
     * 子节点列表
     */
    private List<UserAreaTreeNode> childs = null;
    /**
     * 节点根用户列表
     */
    private List<SelectUser> userInfos = null;

    /**
     * 当前节点是不是被选择
     */
    private boolean isSelected = false;

    public UserAreaTreeNode() {
        childs = new ArrayList<>(5);
        userInfos = new ArrayList<>();
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<UserAreaTreeNode> getChilds() {
        return childs;
    }

    public void setChilds(List<UserAreaTreeNode> childs) {
        this.childs = childs;
    }

    public List<SelectUser> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<SelectUser> userInfos) {
        this.userInfos = userInfos;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * 是不是含有子节点
     *
     * @return
     */
    public boolean hasChilds() {
        if (childs != null && childs.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        return "UserAreaTreeNode{" +
                "area=" + area +
                ", childs=" + childs +
                ", userInfos=" + userInfos +
                '}';
    }
}
