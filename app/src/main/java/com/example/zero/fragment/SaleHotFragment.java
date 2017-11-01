package com.example.zero.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.example.zero.activity.LoginActivity;
import com.example.zero.adapter.SaleHotCouponAdapter;
import com.example.zero.bean.SaleBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.ReqClassUtils;
import com.example.zero.util.ReqJsonUtils;
import com.example.zero.util.RequestManager;
import com.example.zero.util.TypeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jojo on 2017/9/13.
 */

public class SaleHotFragment extends Fragment {

    private View sale_hot_frag;
    private RecyclerView hot_recv;
    private List<SaleBean> dataList = new ArrayList<>();
    private List<SaleBean> dataList1 = new ArrayList<>();
    private List<SaleBean> dataList2 = new ArrayList<>();
    private List<SaleBean> dataList3 = new ArrayList<>();
    private List<SaleBean> dataList4 = new ArrayList<>();
    private SaleHotCouponAdapter adapter;
    private double longitude, latitude;
    private LocationClient mLocationClient;
    private String uid, token;
    private Context context;
    private ImageView coupon_image;
    private ArrayList<String> coupon_id = new ArrayList<>();
    private String coupon_name;
    private int coupon_type;
    private String shop_type;
    private String coupon_price;
    private String coupon_content;
    private String coupon_time;
    private String coupon_img;
//    private ArrayList<String> coupon_name = new ArrayList<>();
//    private ArrayList<String> coupon_price = new ArrayList<>();
//    private ArrayList<String> coupon_content = new ArrayList<>();
//    private ArrayList<String> coupon_time = new ArrayList<>();
//    private ArrayList<Integer> coupon_img = new ArrayList<>();

    private RadioGroup saleHotGroup;
    private RadioButton saleHotFood, saleHotEntertainment, saleHotShopping, saleHotTravel;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        sale_hot_frag = inflater.inflate(R.layout.fragment_sale_hot, container, false);
        context = sale_hot_frag.getContext();
        innitView();
        saleHotFood.setCompoundDrawablesWithIntrinsicBounds(R.drawable.food_checked, 0, 0, 0);
        saleHotEntertainment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.entertainment, 0, 0, 0);
        saleHotShopping.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shopping, 0, 0, 0);
        saleHotTravel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.travel, 0, 0, 0);
        isLocationOpen();
        getCouponData();
        showCouponData(0);
        hot_recv.setAdapter(adapter);
        saleHotGroup = (RadioGroup) sale_hot_frag.findViewById(R.id.sale_hot_rg);
        saleHotGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.sale_hot_food:
                        saleHotFood.setCompoundDrawablesWithIntrinsicBounds(R.drawable.food_checked, 0, 0, 0);//选中时为红色
                        saleHotEntertainment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.entertainment, 0, 0, 0);//未选中的为灰色
                        saleHotShopping.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shopping, 0, 0, 0);
                        saleHotTravel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.travel, 0, 0, 0);
                        showCouponData(0);
                        break;
                    case R.id.sale_hot_entertainment:
                        saleHotFood.setCompoundDrawablesWithIntrinsicBounds(R.drawable.food, 0, 0, 0);
                        saleHotEntertainment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.entertainment_checked, 0, 0, 0);
                        saleHotShopping.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shopping, 0, 0, 0);
                        saleHotTravel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.travel, 0, 0, 0);
                        showCouponData(1);
                        break;
                    case R.id.sale_hot_shopping:
                        saleHotFood.setCompoundDrawablesWithIntrinsicBounds(R.drawable.food, 0, 0, 0);
                        saleHotEntertainment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.entertainment, 0, 0, 0);
                        saleHotShopping.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shopping_checked, 0, 0, 0);
                        saleHotTravel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.travel, 0, 0, 0);
                        showCouponData(2);
                        break;
                    case R.id.sale_hot_travel:
                        saleHotFood.setCompoundDrawablesWithIntrinsicBounds(R.drawable.food, 0, 0, 0);
                        saleHotEntertainment.setCompoundDrawablesWithIntrinsicBounds(R.drawable.entertainment, 0, 0, 0);
                        saleHotShopping.setCompoundDrawablesWithIntrinsicBounds(R.drawable.shopping, 0, 0, 0);
                        saleHotTravel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.travel_checked, 0, 0, 0);
                        showCouponData(3);
                    default:
                        break;
                }
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setOnItemClickListener(new SaleHotCouponAdapter.onRecycleItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context, "点击条目 " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBtnClick(View view, int position) {
                HashMap<String, String> params = new HashMap<>();
                params.put("couponId", coupon_id.get(position));
                params.put("userId", "123");
                params.put("token", "123");
                RequestManager.getInstance(context).requestAsyn("users/me/coupons/" + coupon_id.get(position), RequestManager.TYPE_POST_JSON, params, new RequestManager.ReqCallBack<String>() {

                    @Override
                    public void onReqSuccess(String result) {
                        //System.out.println(result);
                        JSONObject jo = JSON.parseObject(result);
                        Toast.makeText(context, jo.getString("userMessage"), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        JSONObject jo = JSON.parseObject(errorMsg);
                        Toast.makeText(context, jo.getString("userMessage"), Toast.LENGTH_SHORT).show();
                        //System.out.println(errorMsg);
                    }
                });
            }
        });
        return sale_hot_frag;
    }

    /**
     * 初始化view
     */
    private void innitView() {
        hot_recv = (RecyclerView) sale_hot_frag.findViewById(R.id.sale_hot_recv);
        hot_recv.setLayoutManager(new LinearLayoutManager(context));
        saleHotFood = (RadioButton) sale_hot_frag.findViewById(R.id.sale_hot_food);
        saleHotEntertainment = (RadioButton) sale_hot_frag.findViewById(R.id.sale_hot_entertainment);
        saleHotShopping = (RadioButton) sale_hot_frag.findViewById(R.id.sale_hot_shopping);
        saleHotTravel = (RadioButton) sale_hot_frag.findViewById(R.id.sale_hot_travel);
//        SharedPreferences sp = context.getSharedPreferences("GreenTravel", Context.MODE_PRIVATE);
//        uid = sp.getString("user_id", "");
//        token = sp.getString("token", "");
    }

    public void isLocationOpen() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    // Permission Denied
                    Toast.makeText(context, "PERMISSION Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

//    public void getUserData(){
//        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
//        String uid = preferences.getString("uid", "defaultname");
//        String token = preferences.getString("token", "0");
//    }

    public void getCouponData() {
        dataList1.clear();
        dataList2.clear();
        dataList3.clear();
        dataList4.clear();
        mLocationClient = new LocationClient(context);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                // 非空判断
                if (bdLocation != null) {
                    // 根据BDLocation 对象获得经纬度
                    latitude = bdLocation.getLatitude();
                    longitude = bdLocation.getLongitude();
                    if (mLocationClient.isStarted()) {
                        // 获得位置之后停止定位
                        mLocationClient.stop();
                    }
                }
                HashMap<String, String> params = new HashMap<>();
                params.put("longitude", "113.5333880000");
                params.put("latitude", "22.7935870000");
                params.put("userId", "1234567");
                params.put("token", "14561464");
//                if(uid.equals("") && token.equals("")){
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
//                    params.put("userId", "");
//                    params.put("token", "");
//                } else{
//                    params.put("userId", uid);
//                    params.put("token", token);
//                }
                //Toast.makeText(context, "纬度" + latitude, Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, "经度" + longitude, Toast.LENGTH_SHORT).show();
                //异步get请求
                RequestManager.getInstance(context).requestAsyn("users/me/coupons/near", RequestManager.TYPE_GET, params, new RequestManager.ReqCallBack<String>() {

                    @Override
                    public void onReqSuccess(String result) {
                        //Toast.makeText(context, "请求成功", Toast.LENGTH_SHORT).show();
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
                            shop_type = jo.getString("shop_tag");
                            SaleBean saleBean = new SaleBean();
                            saleBean.setText(coupon_name, coupon_price, coupon_content, coupon_time + "到期", coupon_img);
                            switch (shop_type) {
                                case "餐饮":
                                    dataList1.add(saleBean);
                                    break;
                                case "购物":
                                    dataList2.add(saleBean);
                                    break;
                                case "娱乐":
                                    dataList3.add(saleBean);
                                    break;
                                case "旅游":
                                    dataList4.add(saleBean);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        Toast.makeText(context, "热门优惠券请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public void showCouponData(int type) {
        switch (type) {
            case 0:
                adapter = new SaleHotCouponAdapter(context, dataList1);
                break;
            case 1:
                adapter = new SaleHotCouponAdapter(context, dataList2);
                break;
            case 2:
                adapter = new SaleHotCouponAdapter(context, dataList3);
                break;
            case 3:
                adapter = new SaleHotCouponAdapter(context, dataList4);
                break;
            default:
                break;
        }
    }
}


