package com.qdhc.ny.bmob;

import java.io.Serializable;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户账号
 */
public class UserInfo extends BmobUser implements Serializable {

    /**
     * 昵称
     */
    private String nickName = "";

    /**
     * 年龄
     */
    private int age;

    /**
     * 性别
     */
    private int gender;

    /**
     * 头像
     */
    private BmobFile avatar;
    /**
     * 角色ID
     */
    private int role;

    /**
     * 所属乡镇
     *
     * @deprecated 1.1.5开始弃用
     */
    private String district = "";

    /**
     * 管辖区县ID
     *
     * @deprecated 1.1.5开始弃用
     */
    private int areaId;

    /**
     * 所属城市
     */
    private String city = "";

    /**
     * 所属区县
     */
    private String county = "";

    /**
     * 所属乡镇
     */
    private String village = "";

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    @Override
    public String toString() {
        return nickName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }
}
