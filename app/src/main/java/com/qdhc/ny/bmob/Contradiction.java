package com.qdhc.ny.bmob;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 信息对象
 *
 * @Author wj
 * @Date 2019/8/7
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class Contradiction extends BmobObject implements Serializable {

    /**
     * 上报人
     */
    private String partyMan;
    /**
     * 联系电话
     */
    private String partyPhone;
    /**
     * 涉及人数
     */
    private String numbers;
    /**
     *
     */
    private String level;
    /**
     * 上报类型
     */
    private String type;
    /**
     * 描述信息
     */
    private String description;
    /**
     * 上报人ID
     */
    private String uploader;
    /**
     * 处理状态
     */
    private String status;
    /**
     * 处理人ID
     */
    private String resultId;
    /**
     * 处理结果
     */
    private String result;
    /**
     * 街道名称
     */
    private String village;
    /**
     * 项目类型
     */
    private String from;
    /**
     * 批示领导ID
     */
    private String cmtId;
    /**
     * 批示内容
     */
    private String comment;
    /**
     * 所属区域
     */
    private String district;

    public String getPartyMan() {
        return partyMan;
    }

    public void setPartyMan(String partyMan) {
        this.partyMan = partyMan;
    }

    public String getPartyPhone() {
        return partyPhone;
    }

    public void setPartyPhone(String partyPhone) {
        this.partyPhone = partyPhone;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCmtId() {
        return cmtId;
    }

    public void setCmtId(String cmtId) {
        this.cmtId = cmtId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }
}
