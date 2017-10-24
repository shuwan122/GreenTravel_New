package com.example.zero.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zero.bean.MsgBean;
import com.example.zero.greentravel_new.R;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgViewHolder> {

    private Context context;
    private List<MsgBean> dataList;

    public MsgAdapter(Context context, List<MsgBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public MsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MsgViewHolder(LayoutInflater.from(context).inflate(R.layout.msg_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MsgViewHolder holder, int position) {
        holder.title.setText(dataList.get(position).getTitle());
        holder.content.setText(dataList.get(position).getContent());
        holder.time.setText(dataList.get(position).getTime());
        holder.img.setImageResource(dataList.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class MsgViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;
        private TextView time;
        private ImageView img;

        public MsgViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.msg_title);
            content = (TextView) itemView.findViewById(R.id.msg_content);
            time = (TextView) itemView.findViewById(R.id.msg_time);
            img = (ImageView) itemView.findViewById(R.id.msg_img);
        }
    }
}
