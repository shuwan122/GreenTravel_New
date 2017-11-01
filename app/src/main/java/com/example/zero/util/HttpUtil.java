package com.example.zero.util;

import android.os.Bundle;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by ZERO on 2017/10/29.
 */

public class HttpUtil {
    public static void sendSingleOkHttpRequest(Bundle mBundle, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("User_id", mBundle.getString("userId"))
                .add("Start", mBundle.getString("beginStation"))
                .add("End", mBundle.getString("endStation"))
                .build();
        Request request = new Request.Builder()
                .url("http://10.108.120.91:8080/route/single")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendDataOkHttpRequest(okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://10.108.120.154:8080/route/station")
                .build();
        client.newCall(request).enqueue(callback);
    }
}
