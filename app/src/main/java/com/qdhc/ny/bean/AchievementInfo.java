package com.qdhc.ny.bean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by 申健 on 2019/4/16.
 */

public class AchievementInfo {

    /**
     * personBalanceALL : 0
     * averMonthBalanceALL : 0
     * lastMonthBalanceAll : 0
     * personCount : 41
     * dataList : [{"User_ID":362,"SC_ID":82,"bankdataId":3615,"personNum":"81004026","personName":"齐元海","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3616,"personNum":"81004026","personName":"李丹纬","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3617,"personNum":"81004026","personName":"刘泽国","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3618,"personNum":"81004026","personName":"彭贤芬","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3619,"personNum":"81004026","personName":"程泽国","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3620,"personNum":"81004026","personName":"撒的","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3621,"personNum":"81004026","personName":"是多少","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3622,"personNum":"81004026","personName":"胡奥军","zhihang":"","fenhang":"","personBalance":11,"personBalanceStr":"11.00","averMonthBalance":10,"averMonthBalanceStr":"10.00","lastMonthBalance":12,"lastMonthBalanceStr":"12.00","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3623,"personNum":"81004026","personName":"訾方兰","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3624,"personNum":"81004026","personName":"齐元海","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-05-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3625,"personNum":"81004026","personName":"齐元海","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-05-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3627,"personNum":"81004026","personName":"齐元海","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-05-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3629,"personNum":"81004026","personName":"齐元海","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2019-02-19T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3630,"personNum":"81004026","personName":"齐元海","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2019-02-19T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3631,"personNum":"81004026","personName":"陈芳","zhihang":"","fenhang":"","personBalance":1201.36,"personBalanceStr":"1201.36","averMonthBalance":1201.36,"averMonthBalanceStr":"1201.36","lastMonthBalance":1201.36,"lastMonthBalanceStr":"1201.36","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3632,"personNum":"81004026","personName":"陈为娟","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3633,"personNum":"81004026","personName":"袁丽","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":100535.71,"averMonthBalanceStr":"100535.71","lastMonthBalance":-12,"lastMonthBalanceStr":"-12.00","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3634,"personNum":"81004026","personName":"刘丽","zhihang":"","fenhang":"","personBalance":4646505.72,"personBalanceStr":"4646505.72","averMonthBalance":4646505.72,"averMonthBalanceStr":"4646505.72","lastMonthBalance":4634533.03,"lastMonthBalanceStr":"4634533.03","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3635,"personNum":"81004026","personName":"陈立娥","zhihang":"","fenhang":"","personBalance":4195300.94,"personBalanceStr":"4195300.94","averMonthBalance":4195300.94,"averMonthBalanceStr":"4195300.94","lastMonthBalance":2161465.92,"lastMonthBalanceStr":"2161465.92","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3636,"personNum":"81004026","personName":"秦晓","zhihang":"","fenhang":"","personBalance":26611.87,"personBalanceStr":"26611.87","averMonthBalance":26611.87,"averMonthBalanceStr":"26611.87","lastMonthBalance":26265.75,"lastMonthBalanceStr":"26265.75","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3637,"personNum":"81004026","personName":"胡善云","zhihang":"","fenhang":"","personBalance":379338.14,"personBalanceStr":"379338.14","averMonthBalance":379338.14,"averMonthBalanceStr":"379338.14","lastMonthBalance":386308.88,"lastMonthBalanceStr":"386308.88","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3638,"personNum":"81004026","personName":"王淑芹","zhihang":"","fenhang":"","personBalance":4353078.42,"personBalanceStr":"4353078.42","averMonthBalance":4353078.42,"averMonthBalanceStr":"4353078.42","lastMonthBalance":4323067.52,"lastMonthBalanceStr":"4323067.52","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3639,"personNum":"81004026","personName":"王军友","zhihang":"","fenhang":"","personBalance":3572666.33,"personBalanceStr":"3572666.33","averMonthBalance":3572666.33,"averMonthBalanceStr":"3572666.33","lastMonthBalance":3510260.08,"lastMonthBalanceStr":"3510260.08","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3640,"personNum":"81004026","personName":"郭长娟","zhihang":"","fenhang":"","personBalance":386448.68,"personBalanceStr":"386448.68","averMonthBalance":386448.68,"averMonthBalanceStr":"386448.68","lastMonthBalance":392077.06,"lastMonthBalanceStr":"392077.06","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3641,"personNum":"81004026","personName":"范霞","zhihang":"","fenhang":"","personBalance":6032237.6,"personBalanceStr":"6032237.60","averMonthBalance":6032237.6,"averMonthBalanceStr":"6032237.60","lastMonthBalance":6076249.36,"lastMonthBalanceStr":"6076249.36","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3642,"personNum":"81004026","personName":"秦玲","zhihang":"","fenhang":"","personBalance":1201.36,"personBalanceStr":"1201.36","averMonthBalance":1201.36,"averMonthBalanceStr":"1201.36","lastMonthBalance":1201.36,"lastMonthBalanceStr":"1201.36","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3643,"personNum":"81004026","personName":"厉华亮","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3644,"personNum":"81004026","personName":"王丽","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":100535.71,"lastMonthBalanceStr":"100535.71","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3645,"personNum":"81004026","personName":"秦玲","zhihang":"","fenhang":"","personBalance":1201.36,"personBalanceStr":"1201.36","averMonthBalance":1201.36,"averMonthBalanceStr":"1201.36","lastMonthBalance":1201.36,"lastMonthBalanceStr":"1201.36","personDate":"2018-01-01T00:00:00"},{"User_ID":362,"SC_ID":82,"bankdataId":3646,"personNum":"81004026","personName":"厉华亮","zhihang":"","fenhang":"","personBalance":0,"personBalanceStr":"0.00","averMonthBalance":0,"averMonthBalanceStr":"0.00","lastMonthBalance":0,"lastMonthBalanceStr":"0.00","personDate":"2018-01-01T00:00:00"}]
     */

    private float personBalanceALL;
    private float averMonthBalanceALL;
    private float lastMonthBalanceAll;
    private int personCount;
    private List<DataListBean> dataList;

    public String getPersonBalanceALL() {
        return getWan(personBalanceALL);
    }
    public static String  getWan(double price){
        String txt=price+"";
        if (price>10000d) {
            DecimalFormat df = new DecimalFormat("#.00");
            txt = df.format(price/10000d)+"万";
        }
      return txt;
    }
    public void setPersonBalanceALL(float personBalanceALL) {
        this.personBalanceALL = personBalanceALL;
    }

    public String getAverMonthBalanceALL() {
        return getWan(personBalanceALL);
    }

    public void setAverMonthBalanceALL(float averMonthBalanceALL) {
        this.averMonthBalanceALL = averMonthBalanceALL;
    }

    public String getLastMonthBalanceAll() {
        return getWan(personBalanceALL);
    }

    public void setLastMonthBalanceAll(float lastMonthBalanceAll) {
        this.lastMonthBalanceAll = lastMonthBalanceAll;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * User_ID : 362
         * SC_ID : 82
         * bankdataId : 3615
         * personNum : 81004026
         * personName : 齐元海
         * zhihang :
         * fenhang :
         * personBalance : 0
         * personBalanceStr : 0.00
         * averMonthBalance : 0
         * averMonthBalanceStr : 0.00
         * lastMonthBalance : 0
         * lastMonthBalanceStr : 0.00
         * personDate : 2018-01-01T00:00:00
         */
        private String  Nickname;
        private int User_ID;
        private int SC_ID;
        private int bankdataId;

        public String getNickname() {
            return Nickname == null ? "" : Nickname;
        }

        public void setNickname(String nickname) {
            Nickname = nickname;
        }

        private String personNum;
        private String personName;
        private String zhihang;
        private String fenhang;
        private double personBalance;
        private String personBalanceStr;
        private double averMonthBalance;
        private String averMonthBalanceStr;
        private double lastMonthBalance;
        private String lastMonthBalanceStr;
        private String personDate;

        public int getUser_ID() {
            return User_ID;
        }

        public void setUser_ID(int User_ID) {
            this.User_ID = User_ID;
        }

        public int getSC_ID() {
            return SC_ID;
        }

        public void setSC_ID(int SC_ID) {
            this.SC_ID = SC_ID;
        }

        public int getBankdataId() {
            return bankdataId;
        }

        public void setBankdataId(int bankdataId) {
            this.bankdataId = bankdataId;
        }

        public String getPersonNum() {
            return personNum;
        }

        public void setPersonNum(String personNum) {
            this.personNum = personNum;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public String getZhihang() {
            return zhihang;
        }

        public void setZhihang(String zhihang) {
            this.zhihang = zhihang;
        }

        public String getFenhang() {
            return fenhang;
        }

        public void setFenhang(String fenhang) {
            this.fenhang = fenhang;
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

        public String getPersonBalanceStr() {
            return AchievementInfo.getWan(personBalance);
        }

        public void setPersonBalanceStr(String personBalanceStr) {
            this.personBalanceStr = personBalanceStr;
        }



        public String getAverMonthBalanceStr() {
            return
                    AchievementInfo.getWan(averMonthBalance);
        }

        public void setAverMonthBalanceStr(String averMonthBalanceStr) {
            this.averMonthBalanceStr = averMonthBalanceStr;
        }


        public String getLastMonthBalanceStr() {
            return AchievementInfo.getWan(lastMonthBalance);
        }

        public void setLastMonthBalanceStr(String lastMonthBalanceStr) {
            this.lastMonthBalanceStr = lastMonthBalanceStr;
        }

        public String getPersonDate() {
            if (personDate.length()>5){  String dateTime= personDate.split("\\.")[0].replace("T"," ");
                return   dateTime.substring(0,dateTime.length()-12);}
            return "";
        }

        public void setPersonDate(String personDate) {
            this.personDate = personDate;
        }
    }
}
