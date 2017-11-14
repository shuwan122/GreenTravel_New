package com.example.zero.delegate;

import android.content.Context;
import android.view.View;

import com.example.zero.bean.FavorItemBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.ItemViewDelegate;
import com.example.zero.util.RecycleViewHolder;

/**
 * Created by jojo on 2017/11/3.
 */

public class FavorFirstDelegate implements ItemViewDelegate<FavorItemBean> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.favor_item_head;
    }

    @Override
    public boolean isForViewType(FavorItemBean item, int position) {
        return item.getType().equals(FavorItemBean.ITEM) && position == 0;
    }

    @Override
    public void convert(Context context, RecycleViewHolder holder, FavorItemBean favorItemBean, int position) {
        holder.setText(R.id.spot_name, favorItemBean.getName());
        holder.setText(R.id.spot_content, favorItemBean.getContent());
        holder.getView(R.id.other_favor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
