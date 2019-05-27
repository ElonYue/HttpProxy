package com.cheng.httpproxy;

import android.app.Application;

import com.cheng.httpproxy.proxy.HttpProxy;
import com.cheng.httpproxy.proxy.VollyModel;

/**
 * @author : chengyue
 * @version : v1.0
 * @date : 2019/3/29
 * @history : change on v1.0
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpProxy.getInstance().init(VollyModel.getInstance(this));
    }
}
