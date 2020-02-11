package com.qdhc.ny.bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 版本更新对象
 *
 * @Author wj
 * @Date 2019/12/13
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class AppUpdate extends BmobObject {
    /**
     * 版本号
     */
    int versionCode;
    /**
     * 版本名称
     */
    String versionName;
    /**
     * 文件
     */
    BmobFile downloadFile;
    /**
     * 更新描述
     */
    String content;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public BmobFile getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(BmobFile downloadFile) {
        this.downloadFile = downloadFile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AppUpdate{" +
                "versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", downloadFile=" + downloadFile +
                ", content='" + content + '\'' +
                '}';
    }
}
