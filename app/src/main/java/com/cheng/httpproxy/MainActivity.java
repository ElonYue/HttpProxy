package com.cheng.httpproxy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cheng.httpproxy.proxy.HttpCallBack;
import com.cheng.httpproxy.proxy.HttpProxy;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

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
                HttpProxy.getInstance().get(url, null, new HttpCallBack<JsonObject>() {
                    @Override
                    public void onSucc(JsonObject response) {
                        Log.d("Main", response.toString());
                        tv_result.setText(response.toString());
                    }

                    @Override
                    public void onFailure(String error) {
                        super.onFailure(error);
                    }
                });
            }
        });

        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://192.168.1.250:8585/api/User/GetLogin";
                final Map<String, String> params = new HashMap<>();
                params.put("Account", "slhou@qq.com");
                params.put("Pwd", "111111");
                params.put("ClientID", "111111");

                HttpProxy.getInstance().post(url, params, new HttpCallBack<JsonObject>() {
                    @Override
                    public void onSucc(JsonObject response) {
                        Log.d("Main", response.toString());
                        tv_result.setText(response.toString());
                    }

                    @Override
                    public void onFailure(String error) {
                        super.onFailure(error);
                    }

                });


            }
        });

    }
}
