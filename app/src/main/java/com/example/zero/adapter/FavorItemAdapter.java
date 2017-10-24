package com.example.zero.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zero.bean.FavorItemBean;
import com.example.zero.greentravel_new.R;

import java.util.List;

/**
 * Created by jojo on 2017/10/16.
 */

public class FavorItemAdapter extends RecyclerView.Adapter<FavorItemAdapter.FavorItemViewHolder> {
    private final int FIRST_ITEM = 1;
    private final int SECOND_ITEM = 2;
    private Context context;
    private List<FavorItemBean> dataList;

    public FavorItemAdapter(Context context, List<FavorItemBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return FIRST_ITEM;
        } else {
            return SECOND_ITEM;
        }
    }

    @Override
    public FavorItemAdapter.FavorItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FIRST_ITEM) {
            return new FavorItemAdapter.FavorItemViewHolder(LayoutInflater.from(context).inflate(R.layout.favor_item_head, parent, false));
        } else {
            return new FavorItemAdapter.FavorItemViewHolder(LayoutInflater.from(context).inflate(R.layout.favor_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(FavorItemAdapter.FavorItemViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getName());
        holder.content.setText(dataList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class FavorItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView content;

        public FavorItemViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.spot_name);
            content = (TextView) itemView.findViewById(R.id.spot_content);
        }
    }
}
