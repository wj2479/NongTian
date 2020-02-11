package com.qdhc.ny.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 我的客户经理
 * Created by 申健 on 2019/3/25.
 */

public class ClientManagerInfo  implements Parcelable{

    /**
     * personId : 3674
     * personNum : 81046174
     * personName : 崔永芳
     * fenhang : 五莲中支
     * zhihang : 街头支行
     * personSex : 女
     * phone : 13723936883
     * pifuDate : 2018-03-20T00:00:00
     */

    private int personId;
    private String personNum;
    private String personName;
    private String fenhang;
    private String zhihang;
    private String personSex;
    private String phone;
    private String pifuDate;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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
    public String getSurName(){
        if (getPersonName().length()>1){
            return  getPersonName().substring(0,1);
        }else {
            return  getPersonName();
        }
    }
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getFenhang() {
        return fenhang == null ? "" : fenhang;
    }

    public void setFenhang(String fenhang) {
        this.fenhang = fenhang;
    }

    public String getZhihang() {
        return zhihang == null ? "" : zhihang;
    }

    public void setZhihang(String zhihang) {
        this.zhihang = zhihang;
    }

    public String getPersonSex() {
        return personSex == null ? "" : personSex;
    }

    public void setPersonSex(String personSex) {
        this.personSex = personSex;
    }

    public String getPhone() {
        return phone == null ? "" : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPifuDate() {
        if (pifuDate!=null&&pifuDate.length()>5){ String dateTime= pifuDate.split("\\.")[0].replace("T"," ");
            return   dateTime.substring(0,dateTime.length()-9);}
            return "";

    }

    public void setPifuDate(String pifuDate) {
        this.pifuDate = pifuDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.personId);
        dest.writeString(this.personNum);
        dest.writeString(this.personName);
        dest.writeString(this.fenhang);
        dest.writeString(this.zhihang);
        dest.writeString(this.personSex);
        dest.writeString(this.phone);
        dest.writeString(this.pifuDate);
    }

    public ClientManagerInfo() {
    }

    protected ClientManagerInfo(Parcel in) {
        this.personId = in.readInt();
        this.personNum = in.readString();
        this.personName = in.readString();
        this.fenhang = in.readString();
        this.zhihang = in.readString();
        this.personSex = in.readString();
        this.phone = in.readString();
        this.pifuDate = in.readString();
    }

    public static final Creator<ClientManagerInfo> CREATOR = new Creator<ClientManagerInfo>() {
        @Override
        public ClientManagerInfo createFromParcel(Parcel source) {
            return new ClientManagerInfo(source);
        }

        @Override
        public ClientManagerInfo[] newArray(int size) {
            return new ClientManagerInfo[size];
        }
    };
}
