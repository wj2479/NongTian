package com.qdhc.ny.entity;

import java.io.Serializable;

/**
 * 用户角色对象
 */
public class Role implements Serializable {

    /**
     * 管理员角色
     */
    public static final transient int TYPE_ADMIN = 0;
    /**
     * 用户角色
     */
    public static final transient int TYPE_USER = 1;
    /**
     * 管理员角色
     */
    public static final transient int TYPE_MANAGER = 2;

    static final long serialVersionUID = 14564448355L;

    /**
     * 角色ID
     */
    private int id;
    /**
     * 角色编码
     */
    private int code;
    /**
     * 角色描述
     */
    private String desc = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
                "id=" + id +
                ", code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
