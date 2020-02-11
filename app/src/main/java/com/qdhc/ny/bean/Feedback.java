package com.qdhc.ny.bean;

import cn.bmob.v3.BmobObject;

/**
 * @Author wj
 * @Date 2019/8/15
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class Feedback extends BmobObject {

    private String userId;
    private String content;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
