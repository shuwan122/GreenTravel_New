package com.example.zero.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zero.adapter.OrderAdapter;
import com.example.zero.bean.OrderBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.MultiItemTypeAdapter;
import com.example.zero.view.BottomPopwindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jojo on 2017/11/13.
 */

public class OrderConfirmActivity extends AppCompatActivity {

    private List<OrderBean> dataList = new ArrayList<>();
    private RecyclerView order_recy;
    private TextView finalPrice;
    private Button submit;
    private ImageView backArrow;
    private String shopName;
    private String shopId;
    private String shopImg;
    private int count;
    private int size = 100;
    private String[] idList = new String[size];
    private String[] nameList = new String[size];
    private String[] posterList = new String[size];
    private double[] priceList = new double[size];
    private int[] numList = new int[size];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
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
        showGoodsList();
        OrderAdapter adapter = new OrderAdapter(OrderConfirmActivity.this, dataList);
        order_recy.setAdapter(adapter);
        adapter.setOnInnerItemClickListener(new MultiItemTypeAdapter.OnInnerItemClickListener() {
            @Override
            public void onInnerItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (view.getId()) {
                    case R.id.order_discount:
                        Intent intent = new Intent();
                        intent.putExtra("shopId", shopId);
                        intent.putExtra("shopName", shopName);
                        intent.putExtra("shopImg", shopImg);
                        intent.setClass(OrderConfirmActivity.this, ShoppingCartActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.order_deliver:
                        BottomPopwindow popwindow = new BottomPopwindow(OrderConfirmActivity.this);
                        popwindow.showAtLocation(findViewById(R.id.order_confirm_layout), Gravity.BOTTOM, 0, 0);
                        break;
                    default:
                        break;
                }
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
        backArrow = (ImageView) findViewById(R.id.order_confirm_back_arrow);
        submit = (Button) findViewById(R.id.order_button_submit);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(OrderConfirmActivity.this, OrderConfirmActivity.class);
                startActivity(intent);
            }
        });
    }


    private void showGoodsList() {

        dataList.clear();
        OrderBean orderBean = new OrderBean();
        orderBean.setCusName("收货人：吴姿颍");
        orderBean.setCusPhone("13261196573");
        orderBean.setCusAddr("收货地址：北京市海淀区西土城路10号北京邮电大学学生公寓11");
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
        orderBean3.setDiscount("");
        orderBean3.setDeliver("快递 包邮");
        orderBean3.setGoodsCount2("共" + allCount + "件商品   小计:   ");
        orderBean3.setGoodsPrice2("￥ " + sum);
        orderBean3.setItem(OrderBean.GOODSOTHER);
        dataList.add(orderBean3);

        OrderBean orderBean4 = new OrderBean();
        orderBean4.setOrderMsg("由于天气影响，物流可能有延迟");
        orderBean4.setItem(OrderBean.ORDEROTHER);
        dataList.add(orderBean4);

        finalPrice.setText("￥ " + sum);
    }
}
