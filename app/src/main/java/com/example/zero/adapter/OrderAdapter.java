package com.example.zero.adapter;

import android.content.Context;

import com.example.zero.bean.OrderBean;
import com.example.zero.delegate.OrderAdressDelegate;
import com.example.zero.delegate.OrderGoodsDelegate;
import com.example.zero.delegate.OrderGoodsOtherDelegate;
import com.example.zero.delegate.OrderOtherDelegate;
import com.example.zero.delegate.OrderShopDelegate;
import com.example.zero.util.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by jojo on 2017/11/13.
 */

public class OrderAdapter extends MultiItemTypeAdapter<OrderBean> {

    public OrderAdapter(Context context, List<OrderBean> datas) {
        super(context, datas);
        addItemViewDelegate(new OrderAdressDelegate());
        addItemViewDelegate(new OrderShopDelegate());
        addItemViewDelegate(new OrderGoodsDelegate());
        addItemViewDelegate(new OrderGoodsOtherDelegate());
        addItemViewDelegate(new OrderOtherDelegate());
    }

}
