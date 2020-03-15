package com.qdhc.ny.entity;

import java.util.List;

/**
 * 高德地图返回的实时天气数据
 */
public class LiveWeather {

    /**
     * status : 1
     * count : 1
     * info : OK
     * infocode : 10000
     * lives : [{"province":"山东","city":"东港区","adcode":"371102","weather":"晴","temperature":"14","winddirection":"南","windpower":"≤3","humidity":"50","reporttime":"2020-03-12 10:54:09"}]
     */

    private String status;
    private String count;
    private String info;
    private String infocode;
    private List<LivesData> lives;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public List<LivesData> getLives() {
        return lives;
    }

    public void setLives(List<LivesData> lives) {
        this.lives = lives;
    }

    public static class LivesData {
        /**
         * province : 山东
         * city : 东港区
         * adcode : 371102
         * weather : 晴
         * temperature : 14
         * winddirection : 南
         * windpower : ≤3
         * humidity : 50
         * reporttime : 2020-03-12 10:54:09
         */

        private String province;
        private String city;
        private String adcode;
        private String weather;
        private String temperature;
        private String winddirection;
        private String windpower;
        private String humidity;
        private String reporttime;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getWeather() {
            return weather;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getWinddirection() {
            return winddirection;
        }

        public void setWinddirection(String winddirection) {
            this.winddirection = winddirection;
        }

        public String getWindpower() {
            return windpower;
        }

        public void setWindpower(String windpower) {
            this.windpower = windpower;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getReporttime() {
            return reporttime;
        }

        public void setReporttime(String reporttime) {
            this.reporttime = reporttime;
        }
    }
}
