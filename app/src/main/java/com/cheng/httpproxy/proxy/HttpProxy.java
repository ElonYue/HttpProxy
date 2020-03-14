package com.cheng.httpproxy.proxy;

import java.util.Map;

/**
 * @author : chengyue
 * @version : v1.0
 * @date : 2019/3/29
 * @history : change on v1.0
 */
public class HttpProxy implements IHttp {


    private IHttp mHttp;

    private static HttpProxy mInstance;

    /**
     * 懒汉模式创建单例对象
     *
     * @return HttpProxy Instance
     */
    public static HttpProxy getInstance() {

        if (mInstance == null) {
            synchronized (HttpProxy.class) {
                if (mInstance == null) {
                    mInstance = new HttpProxy();
                }
            }
        }
        return mInstance;
    }

    private HttpProxy() {
        mInstance = this;
    }

    public void init(IHttp http) {
        mHttp = http;
    }


    @Override
    public void get(String url, Map<String, String> params, ICallBack callBack) {
        mHttp.get(url, params, callBack);
    }

    @Override
    public void post(String url, Map<String, String> params, ICallBack callBack) {
        mHttp.post(url, params, callBack);
    }
}
