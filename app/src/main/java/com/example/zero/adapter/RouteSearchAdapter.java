package com.example.zero.adapter;

import android.content.Context;

import com.example.zero.bean.RouteSearchBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.CommonAdapter;
import com.example.zero.util.ViewHolder;

import java.util.List;


public class RouteSearchAdapter extends CommonAdapter<RouteSearchBean> {
    public RouteSearchAdapter(Context context, List<RouteSearchBean> data, int layoutId) {
        super(context, data, layoutId);
    }

//    public RouteSearchAdapter(Context context, List<RouteSearchBean> data, int layoutId, String title, String content, String comments) {
//        super(context, data, layoutId, title, content, comments);
//    }

    @Override
    public void convert(ViewHolder holder, int position) {
        holder.setImageResource(R.id.item_search_iv_icon,mData.get(position).getIconId())
                .setText(R.id.item_search_tv_title,mData.get(position).getTitle())
                .setText(R.id.item_search_tv_content,mData.get(position).getContent())
                .setText(R.id.item_search_tv_comments,mData.get(position).getComments());
    }


}
