package com.cheng.httpproxy.proxy;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author : chengyue
 * @version : v1.0
 * @date : 2019/3/29
 * @history : change on v1.0
 */
public abstract class HttpCallBack<T> implements ICallBack {

    @Override
    public void onSuccess(String response) {
        Log.d("HttpCallBack", "===onSuccess String response==");
        Class<?> cla = getClassType(this);
        T t = (T) new Gson().fromJson(response, cla);
        onSucc(t);
    }


    @Override
    public void onFailure(String error) {

    }

    public abstract void onSucc(T t);

    private Class<?> getClassType(Object object) {
        if (null == object) {
            return null;
        }
        Type type = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
