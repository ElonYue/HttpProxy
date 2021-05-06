package com.cy.httpproxy.proxy;

import java.util.Map;

/**
 * @author : chengyue
 * @version : v1.0
 * @date : 2019/3/29
 * @history : change on v1.0
 */
public interface IHttp {

    /**
     * GET 请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    void get(String url, Map<String, String> params, ICallBack callBack);

    /**
     * POST 请求
     *
     * @param url
     * @param params
     * @param callBack
     */
    void post(String url, Map<String, String> params, ICallBack callBack);
}
