package com.example.zero.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zero.adapter.OrderAdapter;
import com.example.zero.bean.OrderBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.MainApplication;
import com.example.zero.util.MultiItemTypeAdapter;
import com.example.zero.util.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jojo on 2017/11/13.
 */

public class OrderConfirmActivity extends AppCompatActivity {

    private static final String TAG = "OrderConfirmActivity";
    private List<OrderBean> dataList = new ArrayList<>();
    private RecyclerView order_recy;
    private TextView finalPrice;
    private Button submit;
    private TextView backArrow;
    private String shopName;
    private String shopId;
    private int count;
    private int size = 100;
    private String addr_id, addr_name, addr_phone;
    private String[] idList = new String[size];
    private String[] nameList = new String[size];
    private String[] posterList = new String[size];
    private double[] priceList = new double[size];
    private int[] numList = new int[size];
    private PopupWindow popupWindow;
    private View contentView;
    private RadioButton rb1, rb2;
    private TextView cancel;
    private View other;
    private OrderAdapter adapter;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        MainApplication application = (MainApplication) getApplication();
        addr_id = application.getAddressId();
        addr_name = application.getAddressName();
        addr_phone = application.getAddressPhone();
        Intent intent = getIntent();
        Bundle mBundle = intent.getExtras();
        shopName = mBundle.getString("shopName");
        shopId = mBundle.getString("shopId");
        count = mBundle.getInt("size");
        idList = mBundle.getStringArray("idList");
        nameList = mBundle.getStringArray("nameList");
        posterList = mBundle.getStringArray("posterList");
        priceList = mBundle.getDoubleArray("priceList");
        numList = mBundle.getIntArray("numList");
        initView();
        adapter = new OrderAdapter(OrderConfirmActivity.this, dataList);
        order_recy.setAdapter(adapter);
        showGoodsList(0);
        adapter.setOnInnerItemClickListener(new MultiItemTypeAdapter.OnInnerItemClickListener() {
            @Override
            public void onInnerItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (view.getId()) {
                    case R.id.order_discount:
                        Intent intent = new Intent();
                        intent.putExtra("shopId", shopId);
                        intent.setClass(OrderConfirmActivity.this, NearShopCouponActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.order_deliver:
                        showPopwindow();
                        break;
                    case R.id.order_cus_addr:
                        Intent intent1 = new Intent();
                        intent1.putExtra("type", "orderConfirm");
                        intent1.setClass(OrderConfirmActivity.this, AddressActivity.class);
                        startActivityForResult(intent1, 1);
                    default:
                        break;
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray goodsList = new JSONArray();
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("good_id", idList[i]);
                    jsonObject.put("goods_num", numList[i]);
                    jsonObject.put("good_price", priceList[i]);
                    goodsList.add(jsonObject);
                }
                MainApplication mainApplication = (MainApplication) getApplication();
                JSONObject order = new JSONObject();
                order.put("coupon_id", "0");
                order.put("products", goodsList.toString());
                order.put("receive_id", "0");
                order.put("remark", "hahaha");
                order.put("seller_id", shopId);
                order.put("token", mainApplication.getToken());
                order.put("user_id", mainApplication.getUser_id());
                order.put("user_source", "ticket");
                Log.d(TAG, order.toString());
                HashMap<String, String> params = new HashMap<>();
                params.put("order", order.toString());
                RequestManager.getInstance(getBaseContext()).requestAsyn("/order/submit", RequestManager.TYPE_POST_JSON, params, new RequestManager.ReqCallBack<String>() {
                    @Override
                    public void onReqSuccess(String result) {
                        JSONObject object = JSONObject.parseObject(result);
                        Toast.makeText(getBaseContext(), object.getString("message"), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        Log.e(TAG, errorMsg);
                    }
                });
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void initView() {
        order_recy = (RecyclerView) findViewById(R.id.order_recy);
        order_recy.setLayoutManager(new LinearLayoutManager(this));
        finalPrice = (TextView) findViewById(R.id.order_final_price);
        backArrow = (TextView) findViewById(R.id.order_confirm_back_arrow);
        submit = (Button) findViewById(R.id.order_button_submit);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    addr_id = data.getStringExtra("id");
                    addr_name = data.getStringExtra("name");
                    addr_phone = data.getStringExtra("phone");
                    showGoodsList(flag);
                }
                break;
            default:
                break;
        }
    }

    private void showGoodsList(int type) {
        dataList.clear();
        OrderBean orderBean = new OrderBean();
        orderBean.setAddrId(addr_id);
        orderBean.setCusName("收货人：" + addr_name);
        orderBean.setCusPhone(addr_phone);
        orderBean.setItem(OrderBean.ADDRESS);
        dataList.add(orderBean);
        OrderBean orderBean1 = new OrderBean();
        orderBean1.setShopName(shopName);
        orderBean1.setIcon(R.drawable.t_mall);
        orderBean1.setItem(OrderBean.SHOP);
        dataList.add(orderBean1);
        double sum = 0;
        int allCount = 0;
        for (int i = 0; i < count; i++) {
            OrderBean orderBean2 = new OrderBean();
            orderBean2.setGoodsName(nameList[i]);
            orderBean2.setGoodsPrice1("￥ " + priceList[i]);
            orderBean2.setGoodsCount1("x" + numList[i]);
            orderBean2.setGoodsPic(posterList[i]);
            orderBean2.setItem(OrderBean.GOODSINFO);
            sum += priceList[i] * numList[i];
            allCount += numList[i];
            dataList.add(orderBean2);
        }
        OrderBean orderBean3 = new OrderBean();
        orderBean3.setDiscount("-￥ " + "30");//TODO:获取店家优惠信息
        if (type == 0) {
            orderBean3.setDeliver("门店自取");
        } else {
            orderBean3.setDeliver("自提柜取货");
        }
        orderBean3.setGoodsCount2("共" + allCount + "件商品   小计:   ");
        orderBean3.setGoodsPrice2("￥ " + sum);
        orderBean3.setItem(OrderBean.GOODSOTHER);
        dataList.add(orderBean3);
        OrderBean orderBean4 = new OrderBean();
        orderBean4.setOrderMsg("温馨提示");
        orderBean4.setItem(OrderBean.ORDEROTHER);
        dataList.add(orderBean4);
        finalPrice.setText("￥ " + sum);

        adapter.notifyDataSetChanged();
    }

    public void showPopwindow() {
        contentView = LayoutInflater.from(OrderConfirmActivity.this).inflate(R.layout.popwindow_order_deliver, null);
        rb1 = (RadioButton) contentView.findViewById(R.id.popwindow_rb1);
        rb2 = (RadioButton) contentView.findViewById(R.id.popwindow_rb2);
        cancel = (TextView) contentView.findViewById(R.id.popwindow_close);
        other = (View) contentView.findViewById(R.id.popwindow_other);
        RelativeLayout rl = (RelativeLayout) contentView.findViewById(R.id.popwindow_rl);
        if (flag == 0) {
            rb1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkbox_on, 0);
            rb2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkbox_off, 0);
        } else if (flag == 1) {
            rb1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkbox_off, 0);
            rb2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkbox_on, 0);
        }
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;
                rb1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkbox_on, 0);
                rb2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkbox_off, 0);
                showGoodsList(flag);
                popupWindow.dismiss();
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;
                rb1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkbox_off, 0);
                rb2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.checkbox_on, 0);
                showGoodsList(flag);
                popupWindow.dismiss();
            }
        });
        popupWindow = new PopupWindow(contentView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);// 取得焦点
        popupWindow.setOutsideTouchable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        popupWindow.setAnimationStyle(R.style.bottom_popwindow);
        rl.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        return true;
                    }
                }
                return false;
            }
        });
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }
}
