package com.qdhc.ny.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 申健 on 2019/3/24.
 */

public class IndexInfo {
    private List<NoticeInfo> notice;
    private List<BannerBean> banner;

    public List<BannerBean> getBanner() {
        if (banner == null) {
            return new ArrayList<>();
        }
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<NoticeInfo> getNotice() {
        if (notice == null) {
            return new ArrayList<>();
        }
        return notice;
    }

    public void setNotice(List<NoticeInfo> notice) {
        this.notice = notice;
    }


    public static class BannerBean {
        /**
         * Id : 4
         * ImgUrl : http://oa.cunlintao.com/uploadImg/201901131636537044933108.jpg
         * Link : null
         * DisplayOrder : 2
         */

        private int Id;
        private String ImgUrl;
        private Object Link;
        private int DisplayOrder;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getImgUrl() {
            return ImgUrl;
        }

        public void setImgUrl(String ImgUrl) {
            this.ImgUrl = ImgUrl;
        }

        public Object getLink() {
            return Link;
        }

        public void setLink(Object Link) {
            this.Link = Link;
        }

        public int getDisplayOrder() {
            return DisplayOrder;
        }

        public void setDisplayOrder(int DisplayOrder) {
            this.DisplayOrder = DisplayOrder;
        }
    }
}
