package com.cheng.httpproxy.proxy;

/**
 * 请求回调
 *
 * @author : chengyue
 * @version : v1.0
 * @date : 2019/3/29
 * @history : change on v1.0
 */
public interface ICallBack {

    void onSuccess(String response);

    void onFailure(String error);
}
