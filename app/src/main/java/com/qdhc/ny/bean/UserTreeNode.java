package com.qdhc.ny.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户节点
 *
 * @Author wj
 * @Date 2020/1/2
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class UserTreeNode {

    /**
     * 节点的标识
     */
    private String objectId = "super";
    /**
     * 节点名字
     */
    private String name = "";
    /**
     * 子节点列表
     */
    private List<UserTreeNode> childs = null;
    /**
     * 节点根用户列表
     */
    private List<UserNode> userInfos = null;

    /**
     * 父节点
     */
    private UserTreeNode parent = null;

    /**
     * 当前节点的深度
     */
    private int level = -1;

    /**
     * 当前节点是不是被选择
     */
    private boolean isSelected = false;

    public UserTreeNode() {
        childs = new ArrayList<>(5);
        userInfos = new ArrayList<>();
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserTreeNode> getChilds() {
        return childs;
    }

    public List<UserNode> getUserInfos() {
        return userInfos;
    }

    /**
     * 添加子节点
     *
     * @param node
     */
    public void addChild(UserTreeNode node) {
        childs.add(node);
    }

    /**
     * 添加子用户
     *
     * @param userInfo
     */
    public void addUser(UserNode userInfo) {
        userInfos.add(userInfo);
    }

    public UserTreeNode getParent() {
        return parent;
    }

    public void setParent(UserTreeNode parent) {
        this.parent = parent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean hasChild() {
        return level < 4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserTreeNode that = (UserTreeNode) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void reset() {
        objectId = "super";
        name = null;
        childs.clear();
        userInfos.clear();
        parent = null;
        level = -1;
        isSelected = false;
    }
}
