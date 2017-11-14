package com.example.zero.delegate;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zero.bean.OrderBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.ItemViewDelegate;
import com.example.zero.util.RecycleViewHolder;

import java.util.HashMap;

/**
 * Created by jojo on 2017/11/14.
 */

public class OrderGoodsDelegate implements ItemViewDelegate<OrderBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_goods_info;
    }

    @Override
    public boolean isForViewType(OrderBean item, int position) {
        return item.getItem().equals(OrderBean.GOODSINFO);
    }

    @Override
    public void convert(Context context, RecycleViewHolder holder, OrderBean orderBean, int position) {
        holder.setText(R.id.order_goods_name, orderBean.getGoodsName());
        holder.setText(R.id.order_goods_type, orderBean.getGoodsType());
        holder.setText(R.id.order_goods_original_price, orderBean.getGoodsPrice1());
        holder.setText(R.id.order_goods_count, orderBean.getGoodsCount1());
        ImageView pic = (ImageView) holder.getView(R.id.order_goods_pic);
        Glide.with(context).load(orderBean.getGoodsPic()).placeholder(R.drawable.loading).into(pic);
    }
}
