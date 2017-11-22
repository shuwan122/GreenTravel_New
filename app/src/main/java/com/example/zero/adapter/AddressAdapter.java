package com.example.zero.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zero.bean.AddressBean;
import com.example.zero.greentravel_new.R;

import java.util.List;

/**
 * Created by jojo on 2017/11/15.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private Context context;
    private List<AddressBean> dataList;
    private onRecycleItemClickListener mClickListener;

    public AddressAdapter(Context context, List<AddressBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void setOnItemClickListener(onRecycleItemClickListener listener) {
        this.mClickListener = listener;
    }

    @Override
    public AddressAdapter.AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false);
        AddressViewHolder holder = new AddressViewHolder(view, mClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(AddressAdapter.AddressViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getName());
        holder.phone.setText(dataList.get(position).getPhone());
        holder.addr.setText(dataList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private onRecycleItemClickListener mListener;
        private TextView name, phone, addr;
        private CheckBox checkBox;
        private TextView edit, delete;
        private LinearLayout address;

        public AddressViewHolder(View itemView, onRecycleItemClickListener listener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.address_customer_name);
            phone = (TextView) itemView.findViewById(R.id.address_customer_phone);
            addr = (TextView) itemView.findViewById(R.id.address_detailed);
            checkBox = (CheckBox) itemView.findViewById(R.id.address_checkbox);
            edit = (TextView) itemView.findViewById(R.id.address_edit);
            delete = (TextView) itemView.findViewById(R.id.address_delete);
            address = (LinearLayout) itemView.findViewById(R.id.address_linearlayout);
            mListener = listener;
            //checkBox.setOnCheckedChangeListener(this);
            address.setOnClickListener(this);
            edit.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.address_linearlayout:
                    mListener.onItemClick(view, getAdapterPosition());
                    break;
                case R.id.address_edit:
                    mListener.onItem1Click(view, getAdapterPosition());
                    break;
                case R.id.address_delete:
                    mListener.onItem2Click(view, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface onRecycleItemClickListener {
        void onItemClick(View view, int position);

        void onItem1Click(View view, int position);

        void onItem2Click(View view, int position);
    }
}
