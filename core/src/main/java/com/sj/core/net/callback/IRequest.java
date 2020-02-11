package com.sj.core.net.callback;


/**
 * 请求开始和结束
 * @author shenjian
 * @date 2018/8/10
 */

public interface IRequest {
    void onRequestStart();
    void onRequestEnd();
}
