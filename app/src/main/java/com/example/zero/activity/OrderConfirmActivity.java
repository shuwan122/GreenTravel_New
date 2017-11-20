package com.example.zero.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zero.adapter.OrderAdapter;
import com.example.zero.bean.OrderBean;
import com.example.zero.greentravel_new.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jojo on 2017/11/13.
 */

public class OrderConfirmActivity extends AppCompatActivity {

    private List<OrderBean> dataList = new ArrayList<>();
    private RecyclerView order_recy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        initView();
        showGoodsList();
    }

    private void initView() {
        order_recy = (RecyclerView) findViewById(R.id.order_recy);
        order_recy.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showGoodsList() {
        //        Intent intent =getIntent();
//        Bundle orderList = getIntent().getExtras();
//        String[] array = orderList.getStringArray("DATA");
        dataList.clear();
        OrderBean orderBean = new OrderBean();
        orderBean.setCusName("收货人：吴姿颍");
        orderBean.setCusPhone("13261196573");
        orderBean.setCusAddr("收货地址：北京市海淀区西土城路10号北京邮电大学学生公寓11");
        orderBean.setItem(OrderBean.ADDRESS);
        dataList.add(orderBean);
        OrderBean orderBean1 = new OrderBean();
        orderBean1.setShopName("XXXX官方旗舰店");
        orderBean1.setIcon(R.drawable.t_mall);
        orderBean1.setItem(OrderBean.SHOP);
        dataList.add(orderBean1);
//        OrderBean orderBean2 = new OrderBean();
//        orderBean2.setGoodsName("vivo X20全面屏手机4G全网通vivox20手机");
//        orderBean2.setGoodsType("机身颜色:玫瑰金，套餐类型:官方标配，存储容量:128G，版本类型:中国大陆");
//        orderBean2.setGoodsPrice1("￥ 3398");
//        orderBean2.setGoodsCount1("x1");
//        orderBean2.setGoodsPic("http://image.baidu.com/search/detail?z=0&word=%E9%98%BF%E5%88%98&hs=0&pn=5&spn=0&di=0&pi=42852102935&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cs=1671441058%2C2818642621&os=&simid=&adpicid=0&lpn=0&fm=&sme=&cg=&bdtype=-1&oriquery=&objurl=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F1b4c510fd9f9d72a776a0ad7dd2a2834359bbbe9.jpg&fromurl=&gsm=0&catename=pcindexhot");
//        orderBean2.setItem(OrderBean.GOODSINFO);
        OrderBean orderBean2 = new OrderBean();
        orderBean2.setGoodsName("雀巢咖啡");
        orderBean2.setGoodsType("分享装");
        orderBean2.setGoodsPrice1("￥ 109");
        orderBean2.setGoodsCount1("x1");
        orderBean2.setGoodsPic("https://img14.360buyimg.com/n0/jfs/t3259/170/5587914266/144572/64259fc5/5873347aN14e34822.jpg");
        orderBean2.setItem(OrderBean.GOODSINFO);
        dataList.add(orderBean2);
        dataList.add(orderBean2);
        OrderBean orderBean3 = new OrderBean();
//        orderBean3.setDiscount("店铺优惠98元，仅付3300");
        orderBean3.setDeliver("快递 包邮");
        orderBean3.setCusMsg("发顺丰快递");
        orderBean3.setGoodsCount2("共2件商品   小计:   ");
        orderBean3.setGoodsPrice2("￥ 6600");
        orderBean3.setItem(OrderBean.GOODSOTHER);
        dataList.add(orderBean3);
        OrderBean orderBean4 = new OrderBean();
        orderBean4.setOrderMsg("由于天气影响，物流可能有延迟");
        orderBean4.setItem(OrderBean.ORDEROTHER);
        dataList.add(orderBean4);
        OrderAdapter adapter = new OrderAdapter(OrderConfirmActivity.this, dataList);
        order_recy.setAdapter(adapter);
        //Toast.makeText(OrderConfirmActivity.this, "suc", Toast.LENGTH_LONG).show();
    }
}
