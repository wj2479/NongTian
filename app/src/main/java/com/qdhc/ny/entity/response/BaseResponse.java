package com.qdhc.ny.entity.response;

import org.json.JSONObject;

/**
 * @Author wj
 * @Date 2020/2/2
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class BaseResponse {

    /**
     * 返回标识
     */
    private int code;
    /**
     * 提示信息
     */
    private String msg;

    private JSONObject result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }
}
