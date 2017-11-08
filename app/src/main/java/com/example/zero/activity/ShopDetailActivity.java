package com.example.zero.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.zero.greentravel_new.R;
import com.example.zero.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class ShopDetailActivity extends AppCompatActivity {

    private Context context;

    private String shopName;

    private static final String TAG = "ShopDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        context = getBaseContext();
        Intent intent = getIntent();
        shopName = intent.getStringExtra("shopName");

        final Bundle mBundle = new Bundle();
        mBundle.putString("userId", "guest");
        mBundle.putString("shopName", shopName);
        // TODO: 2017/10/29  前后端联调
        HttpUtil.sendStationDisplayOkHttpRequest(mBundle, new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ERROR!");
                Toast.makeText(context, "连接服务器失败，请重新尝试！", Toast.LENGTH_LONG).show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
