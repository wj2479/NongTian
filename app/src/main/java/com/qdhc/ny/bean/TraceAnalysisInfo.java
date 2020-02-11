package com.qdhc.ny.bean;

import com.qdhc.ny.R;

/**
 * Created by 申健 on 2019/3/31.
 */

public class TraceAnalysisInfo {

    /**
     * Id : 443
     * AddressAuto : 山东省日照市东港区日照街道香河祥和家园11号楼
     * DescriptionMini : 签单
     * Category : 上班打卡
     * AddDate : 2019-03-31T16:06:46.62
     */

    private int Id;
    private String AddressAuto;
    private String DescriptionMini;
    private String Category;
    private String AddDate;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getAddressAuto() {
        return AddressAuto;
    }

    public void setAddressAuto(String AddressAuto) {
        this.AddressAuto = AddressAuto;
    }

    public String getDescriptionMini() {
        return DescriptionMini;
    }

    public void setDescriptionMini(String DescriptionMini) {
        this.DescriptionMini = DescriptionMini;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getAddDate() {
        if (AddDate!=null)
        {
            String dateTime= AddDate.split("\\.")[0].replace("T"," ");
            return   dateTime.substring(0,dateTime.length()-3);
        }else {
            return "";
        }
    }

    public void setAddDate(String AddDate) {
        this.AddDate = AddDate;
    }

    public int getImgSrc() {
        switch (Category) {
            case "上班打卡":
                return R.drawable.ic_analysis_worker_start;
            case "下班打卡":
                return R.drawable.ic_analysis_worker_stop;
            default:
                return R.drawable.ic_analysis_location_start;

        }
    }
}
