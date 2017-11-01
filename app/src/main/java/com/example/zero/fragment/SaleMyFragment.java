package com.example.zero.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.zero.adapter.SaleMyCouponAdapter;
import com.example.zero.bean.SaleBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.RequestManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jojo on 2017/9/13.
 */

public class SaleMyFragment extends Fragment {

    private View sale_my_frag;
    private RecyclerView my_recv;
    private List<SaleBean> dataList = new ArrayList<>();
    private Context context;
    private ArrayList<String> coupon_id = new ArrayList<>();
    private String coupon_name;
    private int coupon_type;
    private String coupon_price;
    private String coupon_content;
    private String coupon_time;
    private String coupon_img;
    private SaleMyCouponAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sale_my_frag = inflater.inflate(R.layout.fragment_sale_my, container, false);
        context = sale_my_frag.getContext();
        innitView();
        getCouponData();
        adapter = new SaleMyCouponAdapter(context, dataList);
        my_recv.setAdapter(adapter);
        adapter.setOnItemClickListener(new SaleMyCouponAdapter.onRecycleItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "点击条目 " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBtnClick(View view, int position) {
                //Toast.makeText(getContext(), "点击条目按钮 " + position, Toast.LENGTH_SHORT).show();
                OkHttpClient mOkHttpClient = new OkHttpClient();
                HashMap<String, String> params = new HashMap<>();
                params.put("couponId", coupon_id.get(position));
                params.put("userId", "123");
                params.put("token", "123");
                String param = params.toString();
                RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), param);
                Request request = new Request.Builder()
                        .url("http://10.108.112.96:8080//users/me/coupons/" + coupon_id.get(position))
                        .delete(body)
                        .build();
                mOkHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("SaleMyFragment", e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            //String string = response.body().string();
                            //Log.e("SaleMyFragment", "response ----->" + string);
                            //JSONObject jo = JSON.parseObject(string);
                            //Toast.makeText(context, jo.getString("userMessage"), Toast.LENGTH_SHORT).show();
                            Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show();
                        }else {
                            Log.e("SaleMyFragment", "response ----->" + response.body().toString());
                        }
                    }
                });
            }
        });
        return sale_my_frag;
    }

    /**
     * 初始化view
     */
    private void innitView() {
        my_recv = (RecyclerView) sale_my_frag.findViewById(R.id.sale_my_recv);
        my_recv.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getCouponData();
            adapter.notifyDataSetChanged();
        } else {
            // 相当于Fragment的onPause
        }
    }

    /**
     * 优惠券内容加载
     */
    public void getCouponData() {
        dataList.clear();
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", "123");
        params.put("token", "123");
        RequestManager.getInstance(context).requestAsyn("users/me/coupons", RequestManager.TYPE_GET, params, new RequestManager.ReqCallBack<String>() {

            @Override
            public void onReqSuccess(String result) {
                //Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                //System.out.println(result);
                JSONArray array = JSONArray.parseArray(result);
                for (int i = 0; i < array.size(); i++) {
                    String s = array.get(i).toString();
                    JSONObject jo = JSON.parseObject(s);
                    coupon_id.add(jo.getString("id"));
                    coupon_name = jo.getString("shop_name");
                    coupon_type = jo.getInteger("type");
                    if (coupon_type == 1) {
                        String[] str = jo.getString("coupon_name").split("减");
                        coupon_price = "¥ " + str[1];
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
                    dataList.add(saleBean);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                Toast.makeText(context, "我的优惠券请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
