package com.qdhc.ny.bean;

/**
 * 业绩数据
 * Created by 申健 on 2019/4/6.
 */

public class SPContentInfo {

    /**
     * bankdataId : 3615
     * personNum : 81004026
     * personName : 齐元海
     * zhihang : 开发区支行
     * personBalance : 0
     * averMonthBalance : 0
     * lastMonthBalance : 0
     * personDate : 2018-01-01T00:00:00
     * User_ID : 343
     */

    private int bankdataId;
    private String personNum;
    private String personName;
    private String zhihang;
    private double personBalance;
    private double averMonthBalance;
    private double lastMonthBalance;
    private String personDate;
    private int User_ID;

    public int getBankdataId() {
        return bankdataId;
    }

    public void setBankdataId(int bankdataId) {
        this.bankdataId = bankdataId;
    }

    public String getPersonNum() {
        return personNum == null ? "" : personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getPersonName() {
        return personName == null ? "" : personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getZhihang() {
        return zhihang == null ? "" : zhihang;
    }

    public void setZhihang(String zhihang) {
        this.zhihang = zhihang;
    }

    public double getPersonBalance() {
        return personBalance;
    }

    public void setPersonBalance(double personBalance) {
        this.personBalance = personBalance;
    }

    public double getAverMonthBalance() {
        return averMonthBalance;
    }

    public void setAverMonthBalance(double averMonthBalance) {
        this.averMonthBalance = averMonthBalance;
    }

    public double getLastMonthBalance() {
        return lastMonthBalance;
    }

    public void setLastMonthBalance(double lastMonthBalance) {
        this.lastMonthBalance = lastMonthBalance;
    }

    public String getPersonDate() {
        return personDate == null ? "" : personDate;
    }

    public void setPersonDate(String personDate) {
        this.personDate = personDate;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

}
