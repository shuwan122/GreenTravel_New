package com.example.zero.delegate;

import android.content.Context;
import android.util.Log;

import com.example.zero.bean.UserOrderBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.ItemViewDelegate;
import com.example.zero.util.RecycleViewHolder;

/**
 * Created by kazu_0122 on 2017/11/16.
 */

public class UserOrderShopDelegate implements ItemViewDelegate<UserOrderBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_user_shop;
    }

    @Override
    public boolean isForViewType(UserOrderBean item, int position) {
        return item.getItem().equals(UserOrderBean.SHOP);
    }

    @Override
    public void convert(Context context, RecycleViewHolder holder, UserOrderBean userOrderBean, int position) {
        holder.setText(R.id.user_order_shop_name,userOrderBean.getShopName());
        switch (userOrderBean.getState()) {
            case 1:holder.setText(R.id.user_order_shop_state,"等待付款");break;
            case 2:holder.setText(R.id.user_order_shop_state,"等待发货");break;
            case 3:holder.setText(R.id.user_order_shop_state,"等待收货");break;
            case 8:holder.setText(R.id.user_order_shop_state,"等待到店取货");break;
            case 4:holder.setText(R.id.user_order_shop_state,"交易完成");break;
//            case 6:holder.setText(R.id.user_order_shop_state,"交易完成");break;
            case 9:holder.setText(R.id.user_order_shop_state,"订单已取消");break;
            default:holder.setText(R.id.user_order_shop_state,"服务器异常");
                Log.d("UserOrderActivity","异常"+userOrderBean.getState());break;
        }
        holder.setImageResource(R.id.user_order_shop_icon,userOrderBean.getIcon());
    }
}
