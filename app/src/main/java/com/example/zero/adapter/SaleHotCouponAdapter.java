package com.example.zero.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zero.bean.SaleBean;
import com.example.zero.greentravel_new.R;

import java.util.List;

/**
 * HotCoupon适配器
 *
 * @author jojo
 */

public class SaleHotCouponAdapter extends RecyclerView.Adapter<SaleHotCouponAdapter.SaleViewHolder> {
    private Context context;
    private List<SaleBean> dataList;
    private onRecycleItemClickListener mClickListener;

    public SaleHotCouponAdapter(Context context, List<SaleBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    /**
     * 使用时调用这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param listener
     */
    public void setOnItemClickListener(onRecycleItemClickListener listener) {
        this.mClickListener = listener;
    }

    @Override
    public SaleHotCouponAdapter.SaleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_sale_hot_coupon, parent, false);
        SaleViewHolder holder = new SaleViewHolder(view, mClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(SaleHotCouponAdapter.SaleViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getName());
        holder.price.setText(dataList.get(position).getPrice());
        holder.time.setText(dataList.get(position).getTime());
        holder.content.setText(dataList.get(position).getContent());
        Glide.with(context).load(dataList.get(position).getImage()).placeholder(R.drawable.loading).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * ViewHolder的类，用于缓存控件
     */
    public class SaleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private onRecycleItemClickListener mListener;
        private TextView name;
        private TextView price;
        private TextView content;
        private TextView time;
        private ImageView img;
        private LinearLayout coupon;
        private Button btn;

        public SaleViewHolder(View itemView, onRecycleItemClickListener listener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.sale_hot_name);
            price = (TextView) itemView.findViewById(R.id.sale_hot_price);
            content = (TextView) itemView.findViewById(R.id.sale_hot_content);
            time = (TextView) itemView.findViewById(R.id.sale_hot_time);
            img = (ImageView) itemView.findViewById(R.id.sale_hot_img);
            coupon = (LinearLayout) itemView.findViewById(R.id.sale_hot_coupon);
            btn = (Button) itemView.findViewById(R.id.sale_hot_btn);
            mListener = listener;
            coupon.setOnClickListener(this);
            btn.setOnClickListener(this);
        }

        /**
         * 实现OnClickListener接口重写的方法
         *
         * @param view
         */
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.sale_hot_coupon:
                    mListener.onItemClick(view, getAdapterPosition());
                    break;
                case R.id.sale_hot_btn:
                    mListener.onBtnClick(view, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 创建一个回调接口
     */
    public interface onRecycleItemClickListener {
        void onItemClick(View view, int position);

        void onBtnClick(View view, int position);
    }

}
