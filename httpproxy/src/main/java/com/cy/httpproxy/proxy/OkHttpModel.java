package com.cy.httpproxy.proxy;

import android.util.Log;

import com.cy.httpproxy.SSLSocketClient;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
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

    private List<Interceptor> interceptors = new ArrayList<>();

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
//        Log.d(TAG, url);
        String finUrl = url;

        OkHttpClient okHttpClient = getOkHttpClient();
        Request request = new Request.Builder()
                .url(finUrl)
                .get()//默认就是GET请求，可以不写
                .headers(getHeaders(callBack))
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
//                Log.d(TAG, call.toString() + "\n\r" + e.getMessage());
                Platform.get().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (e instanceof SocketTimeoutException) {
                            callBack.onFailure("SocketTimeoutException:" + e.getMessage());
                        } else if (e instanceof ConnectException) {
                            callBack.onFailure("ConnectException:" + e.getMessage());
                        } else {
                            callBack.onFailure(e.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                try {
                    final String result = (response != null && response.body() != null) ?
                            response.body().string() : "";
                    Platform.get().execute(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onSuccess(result);
                        }
                    });
                } catch (final IOException e) {
                    Platform.get().execute(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onFailure(e.getMessage());
                        }
                    });
                }
            }
        });

    }


    @Override
    public void post(String url, Map<String, String> params, final ICallBack callBack) {
//        Log.d(TAG, "[url:] = " + url);
        try {
//            FormBody.Builder formBodybuilder = new FormBody.Builder();
//            addParams(formBodybuilder, params);
//            JSONObject requestJson = JSONObject.parseObject(JSON.toJSONString(params));
//            MediaType mediaType = callBack.getMediaType();
//            RequestBody requestBody = FormBody.create(mediaType, requestJson.toJSONString());

            Request request = new Request.Builder()
                    .url(url)
                    .post((RequestBody) getBody(callBack, params))
                    .headers(getHeaders(callBack))
                    .build();

            OkHttpClient okHttpClient = getOkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
//                    Log.d(TAG, call.toString() + "\n\r" + e.getMessage());
                    Platform.get().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (e instanceof SocketTimeoutException) {
                                callBack.onFailure("SocketTimeoutException:" + e.getMessage());
                            } else if (e instanceof ConnectException) {
                                callBack.onFailure("ConnectException:" + e.getMessage());
                            } else {
                                callBack.onFailure(e.getMessage());
                            }
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) {
                    try {
                        final String result = (response != null && response.body() != null) ?
                                response.body().string() : "";
                        Platform.get().execute(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(result);
                            }
                        });
                    } catch (final IOException e) {
                        Platform.get().execute(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onFailure(e.getMessage());
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            callBack.onFailure(e.getMessage());
        }
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder =  new OkHttpClient.Builder();
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());
        builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
        return builder.build();
    }

    private Object getBody(ICallBack callBack, Map<String, String> params) {
        Object body = callBack.getBody(params);
        if (body != null) {
            return body;
        }
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        return builder.build();
    }

    private Headers getHeaders(ICallBack callBack) {
        Map<String, String> headers = callBack.getHeaders();
        Headers.Builder headersBuilder = new Headers.Builder();
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                headersBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        return headersBuilder.build();
    }

    public void addInterceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
    }
}
