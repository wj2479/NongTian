package com.qdhc.ny.common;

/**
 * 项目级别
 */
public enum ProjectLevel {

    LEVEL_UNKNOWN(-1, ""),
    LEVEL_PROVINCE(1, "省份"),
    LEVEL_CITY(2, "城市"),
    LEVEL_DISTRICT(3, "区县"),
    LEVEL_VILLAGE(4, "乡镇"),
    LEVEL_SETION(5, "标段");

    private int type;    //类型
    private String desc;    //描述

    ProjectLevel(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 根据Key得到枚举的Value
     * 增强for循环遍历，比较判断
     *
     * @return
     */
    public static ProjectLevel getEnumType(int type) {
        ProjectLevel[] levels = ProjectLevel.values();
        for (ProjectLevel level : levels) {
            if (level.getType() == type) {
                return level;
            }
        }
        return ProjectLevel.LEVEL_UNKNOWN;
    }
}
