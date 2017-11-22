package com.example.zero.delegate;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zero.bean.UserOrderBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.ItemViewDelegate;
import com.example.zero.util.RecycleViewHolder;

/**
 * Created by kazu_0122 on 2017/11/16.
 */

public class UserOrderGoodsDelegate implements ItemViewDelegate<UserOrderBean>{
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_user_goods;
    }

    @Override
    public boolean isForViewType(UserOrderBean item, int position) {
        return item.getItem().equals(UserOrderBean.GOODS);
    }

    @Override
    public void convert(Context context, RecycleViewHolder holder, UserOrderBean userOrderBean, int position) {
        holder.setText(R.id.user_order_goods_name,userOrderBean.getGoodsName());
        holder.setText(R.id.user_order_goods_original_price,"￥ "+userOrderBean.getGoodsPrice());
        holder.setText(R.id.user_order_goods_count,"x"+userOrderBean.getGoodsCount());
        holder.setText(R.id.user_order_goods_type,userOrderBean.getGoodsType());
        ImageView img = (ImageView) holder.getView(R.id.user_order_goods_pic);
        Glide.with(context).load(userOrderBean.getGoodsPic()).placeholder(R.drawable.loading).into(img);
    }
}
