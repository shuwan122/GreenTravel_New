package com.example.zero.delegate;

import android.content.Context;
import android.view.View;

import com.example.zero.bean.OrderBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.ItemViewDelegate;
import com.example.zero.util.RecycleViewHolder;

/**
 * Created by jojo on 2017/11/14.
 */

public class OrderOtherDelegate implements ItemViewDelegate<OrderBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_order_other;
    }

    @Override
    public boolean isForViewType(OrderBean item, int position) {
        return item.getItem().equals(OrderBean.ORDEROTHER);
    }

    @Override
    public void convert(Context context, RecycleViewHolder holder, OrderBean orderBean, int position) {
        holder.setText(R.id.order_message, orderBean.getOrdermsg());
    }
}
