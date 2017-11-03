package com.example.zero.util;

import android.os.Bundle;

import java.util.ArrayList;

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
                .url("http://10.108.120.154:8080/route/single")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendAdviceOkHttpRequest(Bundle mBundle, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("User_id", mBundle.getString("userId"))
                .add("Start", mBundle.getString("beginStation"))
                .add("End", mBundle.getString("endStation"))
                .add("timeStart", mBundle.getString("time").substring(0, 5))
                .add("timeEnd", mBundle.getString("time").substring(8, 13))
                .build();
        Request request = new Request.Builder()
                .url("http://10.108.120.154:8080/route/advice")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendMultiOkHttpRequest(Bundle mBundle, okhttp3.Callback callback) {
        int JUD = 0;
        OkHttpClient client = new OkHttpClient();
        StringBuilder starts = new StringBuilder("");
        ArrayList<String> stList = mBundle.getStringArrayList("beginStationList");
        for (int i = 0; i < stList.size(); i++) {
            if (!stList.get(i).equals("")) {
                if (JUD == 0) {
                    starts.append(stList.get(i));
                    JUD = 1;
                } else {
                    starts.append("+");
                    starts.append(stList.get(i));
                }
            }
        }
        RequestBody requestBody = new FormBody.Builder()
                .add("User_id", mBundle.getString("userId"))
                .add("Starts", starts.toString())
                .add("End", mBundle.getString("endStation"))
                .add("Scene", mBundle.getString("Scene"))
                .build();
        Request request = new Request.Builder()
                .url("http://10.108.120.154:8080/route/multi")
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
