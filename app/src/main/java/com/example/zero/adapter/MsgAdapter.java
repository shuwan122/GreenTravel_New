package com.example.zero.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zero.bean.MsgBean;
import com.example.zero.greentravel_new.R;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgViewHolder> {

    private Context context;
    private List<MsgBean> dataList;
    private onRecycleItemClickListener mClickListener;

    public MsgAdapter(Context context, List<MsgBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setOnItemClickListener(onRecycleItemClickListener listener) {
        this.mClickListener = listener;
    }

    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MsgViewHolder(LayoutInflater.from(context).inflate(R.layout.msg_item, parent, false), mClickListener);
    }

    @Override
    public void onBindViewHolder(MsgViewHolder holder, int position) {
        holder.title.setText(dataList.get(position).getTitle());
        holder.content.setText(dataList.get(position).getContent());
        holder.time.setText(dataList.get(position).getTime());
        Glide.with(context).load(dataList.get(position).getImage()).placeholder(R.drawable.loading).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class MsgViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private onRecycleItemClickListener mListener;
        private TextView title;
        private TextView content;
        private TextView time;
        private ImageView img;
        private LinearLayout msg;

        public MsgViewHolder(View itemView, onRecycleItemClickListener listener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.msg_title);
            content = (TextView) itemView.findViewById(R.id.msg_content);
            time = (TextView) itemView.findViewById(R.id.msg_time);
            img = (ImageView) itemView.findViewById(R.id.msg_img);
            msg = (LinearLayout) itemView.findViewById(R.id.msg_ll);
            msg.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface onRecycleItemClickListener {
        void onItemClick(View view, int position);
    }
}
