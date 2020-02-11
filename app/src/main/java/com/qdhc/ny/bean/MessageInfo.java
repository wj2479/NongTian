package com.qdhc.ny.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 消息
 * Created by 申健 on 2019/4/25.
 */

public class MessageInfo implements Parcelable {
    private String title;
    private String addDate;
    private String mesText;
    private int ribaoId;
        private boolean isRead;
    /**
     * Id : 3
     * userid : 362
     */

    private int Id;
    private int userid;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getRibaoId() {
        return ribaoId;
    }

    public void setRibaoId(int ribaoId) {
        this.ribaoId = ribaoId;
    }


    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddDate() {
        if (addDate.length()>5){  String dateTime= addDate.split("\\.")[0].replace("T"," ");
            return   dateTime.substring(5,dateTime.length()-8);}
        return "";
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getMesText() {
        return mesText == null ? "" : mesText;
    }

    public void setMesText(String mesText) {
        this.mesText = mesText;
    }

    public MessageInfo() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.addDate);
        dest.writeString(this.mesText);
        dest.writeInt(this.ribaoId);
        dest.writeByte(this.isRead ? (byte) 1 : (byte) 0);
        dest.writeInt(this.Id);
        dest.writeInt(this.userid);
    }

    protected MessageInfo(Parcel in) {
        this.title = in.readString();
        this.addDate = in.readString();
        this.mesText = in.readString();
        this.ribaoId = in.readInt();
        this.isRead = in.readByte() != 0;
        this.Id = in.readInt();
        this.userid = in.readInt();
    }

    public static final Creator<MessageInfo> CREATOR = new Creator<MessageInfo>() {
        @Override
        public MessageInfo createFromParcel(Parcel source) {
            return new MessageInfo(source);
        }

        @Override
        public MessageInfo[] newArray(int size) {
            return new MessageInfo[size];
        }
    };
}
