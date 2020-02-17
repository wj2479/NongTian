package com.qdhc.ny.entity;

/**
 * @Author wj
 * @Date 2020/2/14
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class SelectUser extends User {
    /**
     * 是否被选中
     */
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
