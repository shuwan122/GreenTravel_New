package com.example.zero.delegate;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.example.zero.bean.OrderBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.ItemViewDelegate;
import com.example.zero.util.RecycleViewHolder;

import java.util.HashMap;

/**
 * Created by jojo on 2017/11/14.
 */

public class OrderGoodsOtherDelegate implements ItemViewDelegate<OrderBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_goods_other;
    }

    @Override
    public boolean isForViewType(OrderBean item, int position) {
        return item.getItem().equals(OrderBean.GOODSOTHER);
    }

    @Override
    public void convert(final Context context, final RecycleViewHolder holder, OrderBean orderBean, final int position) {
        holder.setText(R.id.order_shop_discount_content, orderBean.getDiscount());
        holder.setText(R.id.order_goods_deliver_content, orderBean.getDeliver());
        holder.setText(R.id.order_cus_msg_content, orderBean.getCusMsg());
        holder.setText(R.id.order_goods_final_count, orderBean.getGoodsCount2());
        holder.setText(R.id.order_goods_final_price, orderBean.getGoodsPrice2());
        final LinearLayout discount = holder.getView(R.id.order_discount);
        final LinearLayout deliver = holder.getView(R.id.order_deliver);
        discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getOnInnerItemClickListener().onInnerItemClick(discount, holder, position);
            }
        });
        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getOnInnerItemClickListener().onInnerItemClick(deliver, holder, position);
            }
        });
    }
}
