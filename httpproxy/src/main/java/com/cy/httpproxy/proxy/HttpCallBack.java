package com.cy.httpproxy.proxy;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.MediaType;

/**
 * @author : chengyue
 * @version : v1.0
 * @date : 2019/3/29
 * @history : change on v1.0
 */
public abstract class HttpCallBack<T> implements ICallBack {
    private final String TAG = "HttpCallBack";

    @Override
    public void onSuccess(String response) {
//        Log.d("HttpCallBack", "===onSuccess String response==");
//        Log.w("HttpCallBack", response);
        try {
            Type[] types = getTypes();
            T t;
            if (types == null) {
                Class<?> cla = getClassType(this);
                t = (T) JSON.parseObject(response, cla);
            } else {
                t = (T) JSON.parseObject(response, buildType(getTypes()));
            }
            onSucc(t);
        } catch (Exception e) {
            Log.e(TAG, "请求结果[Exception]：" + Log.getStackTraceString(e));
//           e.printStackTrace();
            onFailure(e.getMessage());
        }
    }


    @Override
    public void onFailure(String error) {

    }

    public abstract void onSucc(T t);

    private Class<?> getClassType(Object object) {
        if (null == object) {
            return null;
        }
        Type superClass = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) superClass).getActualTypeArguments();
        return (Class<?>) params[0];
    }


    @Override
    public Map<String, String> getHeaders() {
        return null;
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.parse("application/json;charset=utf-8");
    }

    public Type[] getTypes() {
        return null;
    }

    private static Type buildType(Type... types) {
        ParameterizedTypeImpl beforeType = null;
        if (types != null && types.length > 0) {
            for (int i = types.length - 1; i > 0; i--) {
                beforeType = new ParameterizedTypeImpl(new Type[]{beforeType == null ? types[i] :
                        beforeType}, null, types[i - 1]);
            }
        }
        return beforeType;
    }

    @Override
    public Object getBody(Map<String, String> params) {
        return null;
    }
}


