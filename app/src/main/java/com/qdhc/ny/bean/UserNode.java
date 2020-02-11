package com.qdhc.ny.bean;

import com.qdhc.ny.bmob.UserInfo;

/**
 * @Author wj
 * @Date 2020/1/4
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class UserNode {
    /**
     * 当前节点是不是被选择
     */
    private boolean isSelected = false;

    private UserInfo userInfo = null;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "UserNode{" +
                "isSelected=" + isSelected +
                ", userInfo=" + userInfo +
                '}';
    }
}
