package com.qdhc.ny.entity;

import java.io.Serializable;

/**
 * 用户角色对象
 */
public class Role implements Serializable {
    /**
     * 角色级别
     */
    public enum LEVEL {
        ADMIN(0),     // 超级管理员
        PROVINCE(1),  // 省级
        CITY(2),      // 市级
        COUNTY(3),    // 区县
        VILLAGE(4),   // 乡镇
        COMPANY(5),   // 公司
        NONE(-1);

        private int value;

        LEVEL(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }


        /**
         * 根据Key得到枚举的Value
         * 普通for循环遍历，比较判断
         *
         * @param key
         * @return
         */
        public static LEVEL getLevel(int key) {
            LEVEL[] levels = LEVEL.values();
            for (int i = 0; i < levels.length; i++) {
                if (levels[i].getValue() == key) {
                    return levels[i];
                }
            }
            return LEVEL.NONE;
        }
    }

    /**
     * 角色取值
     */
    public enum CODE {
        ADMIN(0),     // 超级管理员
        PROVINCE_LEADER(101),     // 领导
        PROVINCE_MANAGER(102),     // 管理员
        PROVINCE_PERSON(103),     // 员工
        CITY_LEADER(201),     // 领导
        CITY_MANAGER(202),     // 管理员
        CITY_PERSON(203),     // 员工
        COUNTY_LEADER(301),     // 领导
        COUNTY_MANAGER(302),     // 管理员
        COUNTY_PERSON(303),     // 员工
        VILLAGE_LEADER(401),     // 领导
        VILLAGE_MANAGER(402),     // 管理员
        VILLAGE_PERSON(403),     // 员工

        COMPANY_MANAGER(501),     // 总监
        SUPERVISOR(502),     // 监理
        NONE(-1);

        private int value;

        CODE(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        /**
         * 根据Key得到枚举的Value
         * 普通for循环遍历，比较判断
         *
         * @param key
         * @return
         */
        public static CODE getCode(int key) {
            CODE[] codes = CODE.values();
            for (int i = 0; i < codes.length; i++) {
                if (codes[i].getValue() == key) {
                    return codes[i];
                }
            }
            return CODE.NONE;
        }

    }

    static final long serialVersionUID = 14564448355L;

    /**
     * 角色编码
     */
    private int code;
    /**
     * 角色级别
     */
    private int level;
    /**
     * 角色描述
     */
    private String desc = "";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Role{" +
                "code=" + code +
                ", level=" + level +
                ", desc='" + desc + '\'' +
                '}';
    }
}
