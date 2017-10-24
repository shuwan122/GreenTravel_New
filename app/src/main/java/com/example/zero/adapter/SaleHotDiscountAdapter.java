package com.example.zero.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zero.bean.SaleBean;
import com.example.zero.greentravel_new.R;

import java.util.List;

/**
 * Created by jojo on 2017/9/12.
 */

public class SaleHotDiscountAdapter extends RecyclerView.Adapter<SaleHotDiscountAdapter.SaleViewHolder> {

    private Context context;
    private List<SaleBean> dataList;

    public SaleHotDiscountAdapter(Context context, List<SaleBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public SaleHotDiscountAdapter.SaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SaleHotDiscountAdapter.SaleViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_sale_hotdiscount, parent, false));
    }

    @Override
    public void onBindViewHolder(SaleHotDiscountAdapter.SaleViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getName());
        holder.price.setText(dataList.get(position).getPrice());
        holder.content.setText(dataList.get(position).getContent());
        holder.img.setImageResource(dataList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class SaleViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView price;
        private TextView content;
        private ImageView img;

        public SaleViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.sale_hot_name);
            price = (TextView) itemView.findViewById(R.id.sale_hot_price);
            content = (TextView) itemView.findViewById(R.id.sale_hot_content);
            img = (ImageView) itemView.findViewById(R.id.sale_hot_img);
        }
    }


}
