package com.qdhc.ny.bean;

/**
 * 公告
 * Created by 申健 on 2019/3/24.
 */

public class NoticeInfo {

    /**
     * Id : 14
     * Title : 客户经理信息完善
     * GonggaoContent : <p>请各管理部9月30日之前将客户经理信息全部录入完毕！</p>
     * AddDate : 2018-09-25T15:19:37.703
     */

    private int Id;
    private String Title;
    private String GonggaoContent;
    private String AddDate;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getGonggaoContent() {
        return GonggaoContent;
    }

    public void setGonggaoContent(String GonggaoContent) {
        this.GonggaoContent = GonggaoContent;
    }

    public String getAddDate() {
        if (AddDate.length()>5){  String dateTime= AddDate.split("\\.")[0].replace("T"," ");
            return   dateTime.substring(0,dateTime.length()-3);}
        return "";

    }

    public void setAddDate(String AddDate) {
        this.AddDate = AddDate;
    }
}
