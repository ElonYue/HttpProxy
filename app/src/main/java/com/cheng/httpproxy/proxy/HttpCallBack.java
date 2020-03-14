package com.cheng.httpproxy.proxy;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.cheng.httpproxy.LogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


/**
 * @author : chengyue
 * @version : v1.0
 * @date : 2019/3/29
 * @history : change on v1.0
 *
 */
public abstract class HttpCallBack<T> implements ICallBack {

    @Override
    public void onSuccess(String response) {
        Log.d("HttpCallBack", "===onSuccess String response==");
        Log.d("HttpCallBack", response);

       try {
           Type[] types = getTypes();
           T t;
           if (types == null) {
               Class<?> cla = getClassType(this);
               t = (T) JSONObject.parseObject(response, cla);
           } else {
               t = (T) JSONObject.parseObject(response, buildType(getTypes()));
           }
           onSucc(t);
       } catch (Exception e) {
           Log.e("HttpCallBack",e.getMessage());
           onFailure(e.getMessage());
       }

//        https://www.cnblogs.com/liqipeng/p/9148545.html
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
        LogUtil.d("getHeaders");
        return null;
    }



    public Type[] getTypes() {
        return null;
    }

    private static Type buildType(Type... types) {
        ParameterizedTypeImpl beforeType = null;
        if (types != null && types.length > 0) {
            for (int i = types.length - 1; i > 0; i--) {
                beforeType = new ParameterizedTypeImpl(new Type[]{beforeType == null ? types[i] : beforeType}, null, types[i - 1]);
            }
        }
        return beforeType;
    }

    @Override
    public String getMediaType() {
        return "application/json;charset=utf-8";
    }

    @Override
    public <T> T getBody(Map<String, String> params) {
        return null;
    }
}


