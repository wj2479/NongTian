package com.qdhc.ny.bean;

import com.lcodecore.ILabel;

/**
 * @Author wj
 * @Date 2019/11/19
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class TagLabel implements ILabel {

    /**
     * 唯一标识  必选
     */
    String id;

    /**
     * 名称
     */
    String name;

    public TagLabel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
