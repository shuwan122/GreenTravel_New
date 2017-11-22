package com.example.zero.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zero.adapter.UserOrderAdapter;
import com.example.zero.bean.UserOrderBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kazu_0122 on 2017/11/15.
 */

public class UserOrderActivity extends Activity {
    private List<UserOrderBean> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageView backArrow;
    private int type;
    private static final String TAG = "UserOrderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);
        type = getIntent().getIntExtra("type",-1);
        recyclerView = (RecyclerView) findViewById(R.id.user_order_recy);
        initData(type);
        UserOrderAdapter adapter = new UserOrderAdapter(UserOrderActivity.this, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        Log.d(TAG, dataList.size() + "");
        backArrow = (ImageView) findViewById(R.id.user_order_back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter.setOnInnerItemClickListener(new MultiItemTypeAdapter.OnInnerItemClickListener() {
            @Override
            public void onInnerItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (view.getId()){
                    case R.id.user_order_cancel:
                        Toast.makeText(getBaseContext(),"cancel",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_order_pay:
                        Toast.makeText(getBaseContext(),"pay",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_order_prolong:
                        Toast.makeText(getBaseContext(),"prolong",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_order_receive:
                        Intent intent = new Intent();
                        intent.setClass(UserOrderActivity.this,QRcodeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.user_order_comment:
                        Toast.makeText(getBaseContext(),"comment",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getBaseContext(),"default",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    public void initData(int type) {
        if(type==-1) {
            for (int i = 0; i < 6; i++) {
                UserOrderBean bean1 = new UserOrderBean();
                bean1.setItem(UserOrderBean.SHOP);
                bean1.setShopName("苏宁易购官方旗舰店");
                bean1.setState(i);
                bean1.setIcon(R.drawable.t_mall);
                bean1.setItem(UserOrderBean.SHOP);
                UserOrderBean bean2 = new UserOrderBean();
                bean2.setGoodsName("vivo X20全面屏手机4G全网通vivox20手机");
                bean2.setGoodsType("机身颜色:玫瑰金，套餐类型:官方标配，存储容量:128G，版本类型:中国大陆");
                bean2.setGoodsPrice(3398);
                bean2.setGoodsCount(1);
                bean2.setGoodsPic("http://image.baidu.com/search/detail?z=0&word=%E9%98%BF%E5%88%98&hs=0&pn=5&spn=0&di=0&pi=42852102935&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cs=1671441058%2C2818642621&os=&simid=&adpicid=0&lpn=0&fm=&sme=&cg=&bdtype=-1&oriquery=&objurl=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F1b4c510fd9f9d72a776a0ad7dd2a2834359bbbe9.jpg&fromurl=&gsm=0&catename=pcindexhot");
                bean2.setItem(UserOrderBean.GOODS);
                UserOrderBean bean3 = new UserOrderBean();
                bean3.setGoodsCount(1);
                bean3.setGoodsPrice(3398);
                bean3.setState(i);
                bean3.setItem(UserOrderBean.ACTION);
                dataList.add(bean1);
                dataList.add(bean2);
                dataList.add(bean3);
            }
        }
        else if(type<3){
            UserOrderBean bean1 = new UserOrderBean();
            bean1.setItem(UserOrderBean.SHOP);
            bean1.setShopName("苏宁易购官方旗舰店");
            bean1.setState(type);
            bean1.setIcon(R.drawable.t_mall);
            bean1.setItem(UserOrderBean.SHOP);
            UserOrderBean bean2 = new UserOrderBean();
            bean2.setGoodsName("vivo X20全面屏手机4G全网通vivox20手机");
            bean2.setGoodsType("机身颜色:玫瑰金，套餐类型:官方标配，存储容量:128G，版本类型:中国大陆");
            bean2.setGoodsPrice(3398);
            bean2.setGoodsCount(1);
            bean2.setGoodsPic("http://image.baidu.com/search/detail?z=0&word=%E9%98%BF%E5%88%98&hs=0&pn=5&spn=0&di=0&pi=42852102935&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cs=1671441058%2C2818642621&os=&simid=&adpicid=0&lpn=0&fm=&sme=&cg=&bdtype=-1&oriquery=&objurl=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F1b4c510fd9f9d72a776a0ad7dd2a2834359bbbe9.jpg&fromurl=&gsm=0&catename=pcindexhot");
            bean2.setItem(UserOrderBean.GOODS);
            UserOrderBean bean3 = new UserOrderBean();
            bean3.setGoodsCount(1);
            bean3.setGoodsPrice(3398);
            bean3.setState(type);
            bean3.setItem(UserOrderBean.ACTION);
            dataList.add(bean1);
            dataList.add(bean2);
            dataList.add(bean3);
        }
        if(type>1){
            UserOrderBean bean1 = new UserOrderBean();
            bean1.setItem(UserOrderBean.SHOP);
            bean1.setShopName("苏宁易购官方旗舰店");
            bean1.setState(type+1);
            bean1.setIcon(R.drawable.t_mall);
            bean1.setItem(UserOrderBean.SHOP);
            UserOrderBean bean2 = new UserOrderBean();
            bean2.setGoodsName("vivo X20全面屏手机4G全网通vivox20手机");
            bean2.setGoodsType("机身颜色:玫瑰金，套餐类型:官方标配，存储容量:128G，版本类型:中国大陆");
            bean2.setGoodsPrice(3398);
            bean2.setGoodsCount(1);
            bean2.setGoodsPic("http://image.baidu.com/search/detail?z=0&word=%E9%98%BF%E5%88%98&hs=0&pn=5&spn=0&di=0&pi=42852102935&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cs=1671441058%2C2818642621&os=&simid=&adpicid=0&lpn=0&fm=&sme=&cg=&bdtype=-1&oriquery=&objurl=http%3A%2F%2Fh.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F1b4c510fd9f9d72a776a0ad7dd2a2834359bbbe9.jpg&fromurl=&gsm=0&catename=pcindexhot");
            bean2.setItem(UserOrderBean.GOODS);
            UserOrderBean bean3 = new UserOrderBean();
            bean3.setGoodsCount(1);
            bean3.setGoodsPrice(3398);
            bean3.setState(type+1);
            bean3.setItem(UserOrderBean.ACTION);
            dataList.add(bean1);
            dataList.add(bean2);
            dataList.add(bean3);
        }

    }
}
