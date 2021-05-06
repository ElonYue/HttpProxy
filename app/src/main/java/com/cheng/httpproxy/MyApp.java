package com.cheng.httpproxy;

import android.app.Application;

import com.cy.httpproxy.proxy.HttpProxy;
import com.cy.httpproxy.proxy.OkHttpModel;

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
//        HttpProxy.getInstance().init(VollyModel.getInstance(this));
        OkHttpModel okHttpModel = OkHttpModel.getInstance();
        okHttpModel.addInterceptor(new CryptoInterceptor());
        okHttpModel.addInterceptor(new LoggingInterceptor());
        HttpProxy.getInstance().init(okHttpModel);
    }
}
