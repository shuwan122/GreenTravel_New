package com.example.zero.delegate;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zero.activity.QRcodeActivity;
import com.example.zero.bean.UserOrderBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.ItemViewDelegate;
import com.example.zero.util.RecycleViewHolder;


/**
 * Created by kazu_0122 on 2017/11/16.
 */

public class UserOrderActionDelegate implements ItemViewDelegate<UserOrderBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_user_goods_action;
    }

    @Override
    public boolean isForViewType(UserOrderBean item, int position) {
        return item.getItem().equals(UserOrderBean.ACTION);
    }

    @Override
    public void convert(Context context, final RecycleViewHolder holder, UserOrderBean userOrderBean, final int position) {
        holder.setText(R.id.user_order_final_price,""+ userOrderBean.getGoodsPrice());
        holder.setText(R.id.user_order_final_count,""+ userOrderBean.getGoodsCount());
        LinearLayout pre = holder.getView(R.id.user_order_pre);
        LinearLayout mid = holder.getView(R.id.user_order_mid);
        LinearLayout after = holder.getView(R.id.user_order_after);
        final View cancel = holder.getView(R.id.user_order_cancel);
        final View pay = holder.getView(R.id.user_order_pay);
        final View prolong = holder.getView(R.id.user_order_prolong);
        final View receive = holder.getView(R.id.user_order_receive);
        final TextView comment = holder.getView(R.id.user_order_comment);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getOnInnerItemClickListener().onInnerItemClick(cancel,holder,position);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getOnInnerItemClickListener().onInnerItemClick(pay,holder,position);
            }
        });
        prolong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getOnInnerItemClickListener().onInnerItemClick(prolong,holder,position);
            }
        });
        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getOnInnerItemClickListener().onInnerItemClick(receive,holder,position);
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.getOnInnerItemClickListener().onInnerItemClick(comment,holder,position);
            }
        });
        //TODO onclick
        switch (userOrderBean.getState()) {
            case 0:{
                pre.setVisibility(View.VISIBLE);
                mid.setVisibility(View.GONE);
                after.setVisibility(View.GONE);
                break;
            }
            case 1:{
                pre.setVisibility(View.GONE);
                mid.setVisibility(View.GONE);
                after.setVisibility(View.GONE);
                break;
            }
            case 2:{
                pre.setVisibility(View.GONE);
                mid.setVisibility(View.VISIBLE);
                after.setVisibility(View.GONE);
                holder.setText(R.id.user_order_receive,"确认收货");
                break;
            }
            case 3:{
                pre.setVisibility(View.GONE);
                mid.setVisibility(View.VISIBLE);
                after.setVisibility(View.GONE);
                holder.setText(R.id.user_order_receive,"现在取货");
                break;
            }
            case 4: {
                pre.setVisibility(View.GONE);
                mid.setVisibility(View.GONE);
                after.setVisibility(View.VISIBLE);
                break;
            }
            case 5:{
                pre.setVisibility(View.GONE);
                mid.setVisibility(View.GONE);
                after.setVisibility(View.VISIBLE);
                comment.setText("追加评价");
                break;
            }
            default:holder.setText(R.id.user_order_shop_state,"服务器异常");break;
        }
    }


}
