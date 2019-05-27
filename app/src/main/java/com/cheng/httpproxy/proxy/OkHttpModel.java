package com.cheng.httpproxy.proxy;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
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

    private OkHttpModel mInstance;

    public OkHttpModel getInstance() {

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
            public void onResponse(Call call, Response response) throws IOException {
                callBack.onSuccess(response.body().toString());
            }
        });

    }

    @Override
    public void post(String url, Map<String, String> params, final ICallBack callBack) {
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        String requestBody = "I am Jdqm.";
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType, requestBody))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                callBack.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }
}
