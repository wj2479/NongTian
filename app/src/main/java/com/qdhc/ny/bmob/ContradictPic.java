package com.qdhc.ny.bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 信息资源
 *
 * @Author wj
 * @Date 2019/8/7
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class ContradictPic extends BmobObject {

    private String contradict;
    private BmobFile file;

    public String getContradict() {
        return contradict;
    }

    public void setContradict(String contradict) {
        this.contradict = contradict;
    }

    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }
}
