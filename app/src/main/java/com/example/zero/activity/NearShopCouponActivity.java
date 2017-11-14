package com.example.zero.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.zero.adapter.SaleHotCouponAdapter;
import com.example.zero.bean.SaleBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.HttpUtil;
import com.example.zero.util.MainApplication;
import com.example.zero.util.RequestManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class NearShopCouponActivity extends AppCompatActivity {

    private RecyclerView hot_recv;

    private List<SaleBean> dataList = new ArrayList<>();
    private List<SaleBean> dataList1 = new ArrayList<>();
    private SaleHotCouponAdapter adapter;
    private ArrayList<String> coupon_id = new ArrayList<>();
    private ArrayList<String> received_id = new ArrayList<>();
    private String coupon_name;
    private int coupon_type;
    private String shop_type;
    private String coupon_price;
    private String coupon_content;
    private String coupon_time;
    private String coupon_img;
    private Boolean couponHas;
    private String uid, token;

    private String shopId;

    private Context context;

    private static final String RECEIVED = "0";
    private static final String TAG = "NearShopCouponActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_shop_coupon);

        context = getBaseContext();
        Intent intent = getIntent();
        shopId = intent.getStringExtra("shopId");

        final Bundle mBundle = new Bundle();
        mBundle.putString("userId", "guest");
        mBundle.putString("shopId", shopId);
        HttpUtil.sendGoodsOkHttpRequest(mBundle, new okhttp3.Callback() {
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

    private void initView() {
        hot_recv = (RecyclerView) findViewById(R.id.sale_hot_recv);
        hot_recv.setLayoutManager(new LinearLayoutManager(context));

        showCouponData();

        adapter = new SaleHotCouponAdapter(context, dataList);
        hot_recv.setAdapter(adapter);
        adapter.setOnItemClickListener(new SaleHotCouponAdapter.onRecycleItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context, "点击条目 " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBtnClick(View view, final int position) {
                MainApplication application = (MainApplication) getApplication();
                uid = application.getUser_id();
                token = application.getToken();
                if (uid == null && token == null) {
                    Toast.makeText(context, "请您先登录再进行操作", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("couponId", coupon_id.get(position));
                    params.put("userId", uid);
                    params.put("token", token);
                    RequestManager.getInstance(context).requestAsyn("users/me/coupons/" + coupon_id.get(position), RequestManager.TYPE_POST_JSON, params, new RequestManager.ReqCallBack<String>() {
                        @Override
                        public void onReqSuccess(String result) {
                            com.alibaba.fastjson.JSONObject jo = JSON.parseObject(result);
                            Toast.makeText(context, jo.getString("userMessage"), Toast.LENGTH_SHORT).show();
                            received_id.set(position, RECEIVED);
                        }

                        @Override
                        public void onReqFailed(String errorMsg) {
                            com.alibaba.fastjson.JSONObject jo = JSON.parseObject(errorMsg);
                            Toast.makeText(context, jo.getString("userMessage"), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray array = jsonObject.getJSONObject("shopInfo").getJSONArray("coupons");

            coupon_id.clear();
            dataList.clear();
            dataList1.clear();
            for (int i = 0; i < array.length(); i++) {
                JSONObject jo = array.getJSONObject(i);
//                couponHas = jo.getBoolean("owned");

                // TODO: 2017/11/14 优惠券已拥有判定
                coupon_id.add(jo.getString("id"));
                received_id.add(jo.getString("id"));
                coupon_name = jo.getString("shop_name");
                coupon_type = jo.getInt("type");

                if (coupon_type == 1) {
                    String[] str = jo.getString("coupon_name").split("减");
                    coupon_price = "¥" + str[1];
                    coupon_content = "消费金额" + str[0] + "可用";
                } else if (coupon_type == 2) {
                    coupon_price = jo.getString("coupon_name").substring(0, 2);
                    coupon_content = "全场商品" + coupon_price + "优惠";
                }

                String[] string = jo.getString("expire_at").split(" ");
                coupon_time = string[0];
                coupon_img = jo.getString("image_url");
                SaleBean saleBean = new SaleBean();
                saleBean.setText(coupon_name, coupon_price, coupon_content, coupon_time + "到期", coupon_img);
                dataList1.add(saleBean);

                initView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showCouponData() {
        dataList.clear();
        dataList.addAll(dataList1);
    }
}
