package com.sj.core.net.callback;

/**
 * 服务器返回的错误代码
 * @author shenjian
 * @date 2018/8/10
 */

public interface IError {
    void onError(int code, String msg);
}
