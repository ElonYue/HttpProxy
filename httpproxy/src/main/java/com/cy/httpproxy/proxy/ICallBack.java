package com.cy.httpproxy.proxy;

import java.util.Map;

import okhttp3.MediaType;

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

    Map<String, String> getHeaders();

    MediaType getMediaType();

    /**
     * 参数自定义
     * @param params
     * @return
     */
    Object getBody(Map<String, String> params);

}
