package com.example.zero.delegate;

import android.content.Context;

import com.example.zero.bean.OrderBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.ItemViewDelegate;
import com.example.zero.util.RecycleViewHolder;

/**
 * Created by jojo on 2017/11/14.
 */

public class OrderShopDelegate implements ItemViewDelegate<OrderBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_order_shop;
    }

    @Override
    public boolean isForViewType(OrderBean item, int position) {
        return item.getItem().equals(OrderBean.SHOP);
    }

    @Override
    public void convert(Context context, RecycleViewHolder holder, OrderBean orderBean, int position) {
        holder.setImageResource(R.id.order_shop_icon, orderBean.getIcon());
        holder.setText(R.id.order_shop_name, orderBean.getShopName());
    }
}
