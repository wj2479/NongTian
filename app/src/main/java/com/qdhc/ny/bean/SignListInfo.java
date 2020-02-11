package com.qdhc.ny.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 签到列表
 * Created by 申健 on 2019/3/29.
 */

public class SignListInfo {

    /**
     * signCurrentDay : 0
     * signCurrentMonth : 2
     * dataList : [{"id":442,"signUserID":343,"SC_ID":77,"LC_ID":33,"Nickname":"kkk明慧敏","personName":"匡梅远","personPhone":"13792025946","Description":"学以致用","Img":"http://oa.cunlintao.com/uploadImg/201903282032266455100057.jpg","ImgDa":["http://oa.cunlintao.com/uploadImg/201903282032266455100057.jpg"],"Category":"下乡宣传","AddressAuto":"山东省日照市东港区日照街道香河祥和家园11号楼","Lat":"35.438136","Lon":"119.488793","CommentScore":null,"CommentUserName":null,"CommentDate":null,"AddDate":"2019-03-28T20:32:27.007"},{"id":441,"signUserID":343,"SC_ID":77,"LC_ID":33,"Nickname":"kkk明慧敏","personName":"匡梅远","personPhone":"13792025946","Description":"签到内容。","Img":"http://oa.cunlintao.com/uploadImg/201903281956140443498652.jpg","ImgDa":["http://oa.cunlintao.com/uploadImg/201903281956140443498652.jpg","http://oa.cunlintao.com/uploadImg/201903281956145384711309.jpg","http://oa.cunlintao.com/uploadImg/201903281956150022206357.jpg"],"Category":"苍老湿","AddressAuto":"山东省日照市东港区日照街道香河祥和家园11号楼","Lat":"35.438134","Lon":"119.488809","CommentScore":null,"CommentUserName":null,"CommentDate":null,"AddDate":"2019-03-28T19:56:15.243"},{"id":397,"signUserID":343,"SC_ID":77,"LC_ID":33,"Nickname":"kkk明慧敏","personName":"刘海霞","personPhone":"13256029859","Description":"已完成更名资料的收集，进行深入业务沟通，表示近期会达到200万。","Img":"","ImgDa":[""],"Category":"下乡宣传","AddressAuto":"山东省日照市莒县城阳街道大果街村","Lat":"35.58","Lon":"118.837","CommentScore":null,"CommentUserName":null,"CommentDate":null,"AddDate":"2018-09-15T17:32:06.917"},{"id":396,"signUserID":343,"SC_ID":77,"LC_ID":33,"Nickname":"kkk明慧敏","personName":"刘海霞","personPhone":"13256029859","Description":"新发展客户经理苏士美，路家村，其丈夫安茂向，该村会计。\n土地赔偿款下发三批，第一批260万，已下发，安茂向已揽存到自己工号下，重点需要做好后期维护工作，对安茂向多走访多沟通多指导，了解揽存中遇到的问题，提升业务的发展。","Img":"","ImgDa":[""],"Category":"下乡宣传","AddressAuto":"山东省日照市莒县城阳街道大果街村","Lat":"35.58","Lon":"118.837","CommentScore":null,"CommentUserName":null,"CommentDate":null,"AddDate":"2018-09-15T17:15:36.287"},{"id":239,"signUserID":343,"SC_ID":77,"LC_ID":33,"Nickname":"kkk明慧敏","personName":"刘海霞","personPhone":"13256029859","Description":"申作香更名彭学梅","Img":"","ImgDa":[""],"Category":"下乡宣传","AddressAuto":"山东省日照市莒县城阳街道大果街村","Lat":"35.58","Lon":"118.837","CommentScore":null,"CommentUserName":null,"CommentDate":null,"AddDate":"2018-08-27T10:44:00.72"}]
     */

    private int signCurrentDay;
    private int signCurrentMonth;
    private List<DataListBean> dataList;

    public int getSignCurrentDay() {
        return signCurrentDay;
    }

    public void setSignCurrentDay(int signCurrentDay) {
        this.signCurrentDay = signCurrentDay;
    }

    public int getSignCurrentMonth() {
        return signCurrentMonth;
    }

    public void setSignCurrentMonth(int signCurrentMonth) {
        this.signCurrentMonth = signCurrentMonth;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean implements Parcelable {
        /**
         * id : 442
         * signUserID : 343
         * SC_ID : 77
         * LC_ID : 33
         * Nickname : kkk明慧敏
         * personName : 匡梅远
         * personPhone : 13792025946
         * Description : 学以致用
         * Img : http://oa.cunlintao.com/uploadImg/201903282032266455100057.jpg
             * ImgDa : ["http://oa.cunlintao.com/uploadImg/201903282032266455100057.jpg"]
         * Category : 下乡宣传
         * AddressAuto : 山东省日照市东港区日照街道香河祥和家园11号楼
         * Lat : 35.438136
         * Lon : 119.488793
         * CommentScore : null
         * CommentUserName : null
         * CommentDate : null
         * AddDate : 2019-03-28T20:32:27.007
         */

        private int id;
        private int signUserID;
        private int SC_ID;
        private int LC_ID;
        private String Nickname;
        private String personName;
        private String personPhone;
        private String Description;
        private String Img;
        private String Category;
        private String AddressAuto;
        private double Lat;
        private double Lon;
        private int CommentScore;
        private String CommentUserName;
        private String CommentDate;
        private String CommentDescription;
        private String AddDate;
        private List<String> ImgDa;

        public String getCommentDescription() {
            return CommentDescription == null ? "" : CommentDescription;
        }

        public void setCommentDescription(String commentDescription) {
            CommentDescription = commentDescription;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSignUserID() {
            return signUserID;
        }

        public void setSignUserID(int signUserID) {
            this.signUserID = signUserID;
        }

        public int getSC_ID() {
            return SC_ID;
        }

        public void setSC_ID(int SC_ID) {
            this.SC_ID = SC_ID;
        }

        public int getLC_ID() {
            return LC_ID;
        }

        public void setLC_ID(int LC_ID) {
            this.LC_ID = LC_ID;
        }

        public String getNickname() {
            return Nickname == null ? "" : Nickname;
        }

        public void setNickname(String nickname) {
            Nickname = nickname;
        }

        public String getPersonName() {
            return personName == null ? "暂无" : personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public String getPersonPhone() {
            return personPhone == null ? "" : personPhone;
        }

        public void setPersonPhone(String personPhone) {
            this.personPhone = personPhone;
        }

        public String getDescription() {
            return Description == null ? "" : Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getImg() {
            return Img == null ? "" : Img;
        }

        public void setImg(String img) {
            Img = img;
        }

        public String getCategory() {
            return Category == null ? "" : Category;
        }

        public void setCategory(String category) {
            Category = category;
        }

        public String getAddressAuto() {
            if (AddressAuto == null){
                return "";
            }else {
                if (AddressAuto.length()<15){
                    return AddressAuto;
                }else
                if (AddressAuto.length()<30){
                    return   AddressAuto.substring(0,15)+"\n"+AddressAuto.substring(15,AddressAuto.length());
                }
            }
            return "";
        }

        public void setAddressAuto(String addressAuto) {
            AddressAuto = addressAuto;
        }

        public double getLat() {
            return Lat;
        }

        public void setLat(double lat) {
            Lat = lat;
        }

        public double getLon() {
            return Lon;
        }

        public void setLon(double lon) {
            Lon = lon;
        }

        public int getCommentScore() {
            return CommentScore;
        }

        public void setCommentScore(int commentScore) {
            CommentScore = commentScore;
        }

        public String getCommentUserName() {
            return CommentUserName == null ? "-" : CommentUserName;
        }

        public void setCommentUserName(String commentUserName) {
            CommentUserName = commentUserName;
        }

        public String getCommentDate() {
            return CommentDate == null ? "-" : CommentDate;
        }

        public void setCommentDate(String commentDate) {
            CommentDate = commentDate;
        }

        public void setAddDate(String addDate) {
            AddDate = addDate;
        }

        public List<String> getImgDa() {
            if (ImgDa == null) {
                return new ArrayList<>();
            }
            return ImgDa;
        }

        public void setImgDa(List<String> imgDa) {
            ImgDa = imgDa;
        }

        public static Creator<DataListBean> getCREATOR() {
            return CREATOR;
        }

        public DataListBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.signUserID);
            dest.writeInt(this.SC_ID);
            dest.writeInt(this.LC_ID);
            dest.writeString(this.Nickname);
            dest.writeString(this.personName);
            dest.writeString(this.personPhone);
            dest.writeString(this.Description);
            dest.writeString(this.Img);
            dest.writeString(this.Category);
            dest.writeString(this.AddressAuto);
            dest.writeDouble(this.Lat);
            dest.writeDouble(this.Lon);
            dest.writeInt(this.CommentScore);
            dest.writeString(this.CommentUserName);
            dest.writeString(this.CommentDate);
            dest.writeString(this.CommentDescription);
            dest.writeString(this.AddDate);
            dest.writeStringList(this.ImgDa);
        }

        protected DataListBean(Parcel in) {
            this.id = in.readInt();
            this.signUserID = in.readInt();
            this.SC_ID = in.readInt();
            this.LC_ID = in.readInt();
            this.Nickname = in.readString();
            this.personName = in.readString();
            this.personPhone = in.readString();
            this.Description = in.readString();
            this.Img = in.readString();
            this.Category = in.readString();
            this.AddressAuto = in.readString();
            this.Lat = in.readDouble();
            this.Lon = in.readDouble();
            this.CommentScore = in.readInt();
            this.CommentUserName = in.readString();
            this.CommentDate = in.readString();
            this.CommentDescription = in.readString();
            this.AddDate = in.readString();
            this.ImgDa = in.createStringArrayList();
        }

        public static final Creator<DataListBean> CREATOR = new Creator<DataListBean>() {
            @Override
            public DataListBean createFromParcel(Parcel source) {
                return new DataListBean(source);
            }

            @Override
            public DataListBean[] newArray(int size) {
                return new DataListBean[size];
            }
        };
    }
}
