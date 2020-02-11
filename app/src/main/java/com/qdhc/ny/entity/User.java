package com.qdhc.ny.entity;

/**
 * @Author wj
 * @Date 2020/2/2
 * @Desc
 * @Url http://www.chuangze.cn
 */

import java.io.Serializable;

/**
 * 用户
 */
public class User implements Serializable {

    static final long serialVersionUID = 17781235223L;

    /**
     * 用户ID ，主键
     */
    private int id;

    /**
     * 用户名
     */
    private String userName = "";

    /**
     * 昵称
     */
    private String nickName = "";

    /**
     * 手机号码
     */
    private String phone = "";

    /**
     * 手机号是否验证
     */
    private boolean phoneVerified;

    /**
     * 所属区域
     */
    private Area area;

    /**
     * 角色类型
     */
    private int role;

    /**
     * 年龄
     */
    private int age;

    /**
     * 性别
     */
    private int gender;

    /**
     * 头像地址
     */
    private String avater = "";

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneVerified=" + phoneVerified +
                ", area=" + area +
                ", role=" + role +
                ", age=" + age +
                ", gender=" + gender +
                ", avater='" + avater + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
