package com.qdhc.ny.entity.response;

import com.qdhc.ny.entity.User;

/**
 * @Author wj
 * @Date 2020/2/2
 * @Desc
 * @Url http://www.chuangze.cn
 */
public class UserResponse extends BaseResponse {

    static final long serialVersionUID = 177812355L;

    private User result;


    @Override
    public String toString() {
        return "UserResponse{" +
                "result=" + result +
                '}';
    }
}
