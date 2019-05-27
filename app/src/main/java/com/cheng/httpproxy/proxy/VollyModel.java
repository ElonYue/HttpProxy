package com.cheng.httpproxy.proxy;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Volly 实现请求
 *
 * @author : chengyue
 * @version : v1.0
 * @date : 2019/3/29
 * @history : change on v1.0
 */
public class VollyModel implements IHttp {

    private final String TAG = "VollyModel";
    private RequestQueue requestQueue;
    private static VollyModel mInstance;

    public static VollyModel getInstance(Context context) {
        if (mInstance == null) {
            synchronized (VollyModel.class) {
                if (mInstance == null) {
                    mInstance = new VollyModel(context);
                }
            }
        }
        return mInstance;
    }

    private VollyModel(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void get(String url, Map<String, String> params, final ICallBack callBack) {
        String finUrl = HttpUtils.appendParams(url, params);
        StringRequest stringRequest = new StringRequest(finUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callBack.onSuccess(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse : " + error.getMessage());
                callBack.onFailure(error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void post(String url, Map<String, String> params, ICallBack callBack) {

    }

    public static class HttpUtils {
        static String appendParams(String url, Map<String, String> params) {
            if (params == null) {
                return url;
            }
            StringBuffer sb = new StringBuffer();
            sb.append(url);
            for (Map.Entry<String, String> set : params.entrySet()) {
                sb.append(set.getKey()).append(":").append(set.getValue());
            }

            return sb.toString();
        }
    }
}
