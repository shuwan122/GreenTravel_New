package com.example.zero.delegate;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.zero.bean.FavorItemBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.ItemViewDelegate;
import com.example.zero.util.RecycleViewHolder;

/**
 * Created by jojo on 2017/11/3.
 */

public class FavorButtonDelegate implements ItemViewDelegate<FavorItemBean> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.favor_add_button;
    }

    @Override
    public boolean isForViewType(FavorItemBean item, int position) {
        return item.getType().equals(FavorItemBean.BUTTON);
    }

    @Override
    public void convert(Context context, final RecycleViewHolder holder, FavorItemBean favorItemBean, final int position) {
        holder.getView(R.id.favor_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "add" + position, Toast.LENGTH_LONG).show();
                //TODO:添加收藏
            }
        });
    }
}
