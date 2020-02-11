package com.qdhc.ny.bmob;

import cn.bmob.v3.BmobObject;

/**
 * @Author wj
 * @Date 2019/11/7
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class Notice extends BmobObject {

    /**
     * 标题
     */
    String title;

    /**
     * 内容
     */
    String content;
    /**
     * 发布者
     */
    String publish;

    /**
     * 目标 暂未使用
     */
    String target;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publish='" + publish + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
