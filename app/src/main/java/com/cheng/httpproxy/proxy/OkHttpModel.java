package com.cheng.httpproxy.proxy;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author : chengyue
 * @version : v1.0
 * @date : 2019/3/30
 * @history : change on v1.0
 */
public class OkHttpModel implements IHttp {
    private final String TAG = "OkHttpModel";

    private static OkHttpModel mInstance;

    public static OkHttpModel getInstance() {

        if (mInstance == null) {
            synchronized (OkHttpModel.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpModel();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void get(String url, Map<String, String> params, final ICallBack callBack) {
        String finUrl = url;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(finUrl)
                .get()//默认就是GET请求，可以不写
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Platform.get().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callBack.onSuccess(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void post(String url, Map<String, String> params, final ICallBack callBack) {
        FormBody.Builder builder = new FormBody.Builder();
        addParams(builder, params);
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                callBack.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                Platform.get().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callBack.onSuccess(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void addParams(FormBody.Builder builder, Map<String, String> params) {
        if (params != null) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
    }
}
