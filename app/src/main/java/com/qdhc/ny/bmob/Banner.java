package com.qdhc.ny.bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @Author wj
 * @Date 2019/11/7
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class Banner extends BmobObject {

    BmobFile path;

    public BmobFile getPath() {
        return path;
    }

    public void setPath(BmobFile path) {
        this.path = path;
    }
}
