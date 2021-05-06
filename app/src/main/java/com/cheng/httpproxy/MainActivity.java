package com.cheng.httpproxy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.cy.httpproxy.proxy.HttpCallBack;
import com.cy.httpproxy.proxy.HttpProxy;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv_result = findViewById(R.id.tv_result);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://t.weather.sojson.com/api/weather/city/101030100";
                HttpProxy.getInstance().get(url, null, new HttpCallBack<JSONObject>() {
                    @Override
                    public void onSucc(JSONObject response) {
                        Log.d("Main", response.toString());
                        tv_result.setText(response.toString());
                    }

                    @Override
                    public void onFailure(String error) {
                        super.onFailure(error);
                        Log.d("Main", "error : " + error);
                    }
                });
            }
        });

        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "http://192.168.1.250:8585/api/User/GetLogin";
                final Map<String, String> params = new HashMap<>();
//                params.put("Account", "slhou@qq.com");
//                params.put("Pwd", "111111");
//                params.put("ClientID", "111111");
//
//                HttpProxy.getInstance().post(url, params, new HttpCallBack<JSONObject>() {
//                    @Override
//                    public void onSucc(JSONObject response) {
//                        Log.d("Main", response.toString());
//                        tv_result.setText(response.toString());
//                    }
//
//                    @Override
//                    public void onFailure(String error) {
//                        Log.d("Main", error);
//                        super.onFailure(error);
//                    }
//
//                });
                String signUrl = "http://192.162.130.111/secure-gateway-api/v1/psam/card/sign/apply";
                HttpProxy.getInstance().post(signUrl, params,
                        new HttpCallBack<JSONObject>() {
                            @Override
                            public void onSucc(JSONObject response) {
                                LogUtil.d(response.toJSONString());
                            }

                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<>();
                                headers.put("Content-type", "application/json");
                                headers.put("X-Auth-Basic", "Y3B1MDE6MzIx");
                                return headers;
                            }

                            @Override
                            public void onFailure(String error) {
                            }

                            @Override
                            public Object getBody(Map<String, String> params) {
                                JSONObject requestJson =
                                        JSONObject.parseObject("{\"areaNo\":\"3401\"," +
                                                "\"encoding\":\"UTF-8\",\"format\":\"JSON\",\"mac\":\"00000000\",\"operatorId\":\"3401000000000001\",\"random\":\"5FE1C3558AB84A87\",\"reqParamsSet\":\"{\\\"laneNo\\\":\\\"3401\\\",\\\"laneType\\\":\\\"03\\\",\\\"proListNo\\\":\\\"34010920200228122618IZUADJECMYFXUIUE\\\",\\\"provinceCode\\\":\\\"3401\\\",\\\"psamNo\\\":\\\"34010201000000000001\\\",\\\"roadCode\\\":\\\"3401\\\",\\\"roadName\\\":\\\"某某高速\\\",\\\"stationCode\\\":\\\"102\\\",\\\"stationName\\\":\\\"包河大道\\\",\\\"stationType\\\":\\\"01\\\",\\\"terminalNo\\\":\\\"215100000001\\\",\\\"terminalTime\\\":\\\"2020-02-28 12:26:18\\\"}\",\"tokenCode\":\"000000000000000000000000000000000000\"}");
                                MediaType mediaType = getMediaType();
                                RequestBody requestBody = FormBody.create(mediaType,
                                        requestJson.toJSONString());
                                return requestBody;
                            }
                        });

            }
        });
    }
}
