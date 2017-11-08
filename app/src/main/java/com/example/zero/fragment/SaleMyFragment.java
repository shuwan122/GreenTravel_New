package com.example.zero.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zero.adapter.SaleMyCouponAdapter;
import com.example.zero.bean.SaleBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.MainApplication;
import com.example.zero.util.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jojo on 2017/9/13.
 */

public class SaleMyFragment extends Fragment {

    private String TAG = "SaleMyFragment";
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
    private String uid, token;

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
                MainApplication application = (MainApplication) getActivity().getApplication();
                uid = application.getUser_id();
                token = application.getToken();
                if (uid == null && token == null) {
                    Toast.makeText(context, "请您先登录再进行操作", Toast.LENGTH_LONG).show();
                } else {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("token", uid);
                    params.put("userId", token);
                    RequestManager.getInstance(context).requestAsyn("users/me/coupons/" + coupon_id.get(position), RequestManager.TYPE_DEL, params, new RequestManager.ReqCallBack<String>() {

                        @Override
                        public void onReqSuccess(String result) {
                            Toast.makeText(context, "优惠券使用成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onReqFailed(String errorMsg) {
                            JSONObject jo = JSON.parseObject(errorMsg);
                            Toast.makeText(context, jo.getString("userMessage"), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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

    //TODO:fragment切换刷新
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            getCouponData();
//            adapter.notifyDataSetChanged();
//        } else {
//            // 相当于Fragment的onPause
//        }
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getCouponData();
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 优惠券内容加载
     */
    public void getCouponData() {
        MainApplication application = (MainApplication) getActivity().getApplication();
        uid = application.getUser_id();
        token = application.getToken();
        if (uid == null && token == null) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    AlertDialog alertDialog = builder.setTitle("登录提醒").setMessage("您还未登录，是否先去登录?")
//                            .setNegativeButton("取消", null)
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent intent = new Intent();
//                                    intent.setClass(context, LoginActivity.class);
//                                    startActivity(intent);
//                                }
//                            }).create();
//                    alertDialog.show();
            Toast.makeText(context, "请您先登录再进行操作", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, String> params = new HashMap<>();
            params.put("userId", uid);
            params.put("token", token);
            RequestManager.getInstance(context).requestAsyn("users/me/coupons?", RequestManager.TYPE_GET, params, new RequestManager.ReqCallBack<String>() {

                @Override
                public void onReqSuccess(String result) {
                    dataList.clear();
                    JSONArray array = JSONArray.parseArray(result);
                    for (int i = 0; i < array.size(); i++) {
                        String s = array.get(i).toString();
                        JSONObject jo = JSON.parseObject(s);
                        coupon_id.add(jo.getString("id"));
                        coupon_name = jo.getString("shop_name");
                        coupon_type = jo.getInteger("type");
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
                        dataList.add(saleBean);
                    }
                }

                @Override
                public void onReqFailed(String errorMsg) {
                    Log.e(TAG, errorMsg);
                    Toast.makeText(context, "我的优惠券请求失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
