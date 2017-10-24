package com.example.zero.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zero.bean.FriendItemBean;
import com.example.zero.greentravel_new.R;

import java.util.List;

/**
 * Created by jojo on 2017/10/24.
 */

public class FriendItemAdapter extends RecyclerView.Adapter<FriendItemAdapter.FriendItemViewHolder> {

    private final int FIRST_ITEM = 1;
    private final int SECOND_ITEM = 2;
    private Context context;
    private List<FriendItemBean> dataList;

    public FriendItemAdapter(Context context, List<FriendItemBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return FIRST_ITEM;
//        } else {
//            return SECOND_ITEM;
//        }
//    }

    @Override
    public FriendItemAdapter.FriendItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FriendItemAdapter.FriendItemViewHolder(LayoutInflater.from(context).inflate(R.layout.friends_item, parent, false));
    }

    @Override
    public void onBindViewHolder(FriendItemAdapter.FriendItemViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getName());
        holder.phone.setText(dataList.get(position).getPhone());
        holder.img.setImageResource(dataList.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class FriendItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView phone;
        private ImageView img;

        public FriendItemViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.friends_name);
            phone = (TextView) itemView.findViewById(R.id.friends_phone);
            img = (ImageView) itemView.findViewById(R.id.friends_img);
        }
    }
}
