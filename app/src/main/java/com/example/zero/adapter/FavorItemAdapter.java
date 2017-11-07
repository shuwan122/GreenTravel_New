package com.example.zero.adapter;

import android.content.Context;

import com.example.zero.bean.FavorItemBean;
import com.example.zero.delegate.FavorButtonDelegate;
import com.example.zero.delegate.FavorFirstDelegate;
import com.example.zero.delegate.FavorLatterDelegate;
import com.example.zero.util.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by jojo on 2017/10/16.
 */

public class FavorItemAdapter extends MultiItemTypeAdapter<FavorItemBean> {
    public FavorItemAdapter(Context context, List<FavorItemBean> datas) {
        super(context, datas);
        addItemViewDelegate(new FavorButtonDelegate());
        addItemViewDelegate(new FavorFirstDelegate());
        addItemViewDelegate(new FavorLatterDelegate());
    }
//    private final int FIRST_ITEM = 1;
//    private final int SECOND_ITEM = 2;
//    private final int BUTTON_ITEM = 3;
//    private Context context;
//    private List<FavorItemBean> dataList;
//    //private View.OnClickListener mClickListener;
//    private onRecycleItemClickListener mClickListener;
//   // private onBtnItemClickListener btnClickListener;
//
//    public FavorItemAdapter(Context context, List<FavorItemBean> dataList) {
//        this.context = context;
//        this.dataList = dataList;
//    }
//
//    public void setOnItemClickListener(onRecycleItemClickListener listener) {
//        this.mClickListener = listener;
//    }
////    public void setOnItem(View.OnClickListener listener) {
////        this.mClickListener = listener;
////    }
//
////    public void setOnBtnClickListener(onBtnItemClickListener listener) {
////        this.btnClickListener = listener;
////    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return FIRST_ITEM;
//        } else if (dataList.get(position).getType().equals(FavorItemBean.BUTTON)) {
//            return BUTTON_ITEM;
//        } else {
//            return SECOND_ITEM;
//        }
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view;
//        if (viewType == FIRST_ITEM) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favor_item_head, parent, false);
//            return new FavorItemViewHolder(view, mClickListener);
////        } else if (viewType == BUTTON_ITEM) {
////            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favor_add_button, parent, false);
////            return new ButtonViewHolder(view, mClickListener);
//        } else {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favor_item, parent, false);
//            return new FavorItemViewHolder(view, mClickListener);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        if (holder instanceof FavorItemViewHolder) {
//            final FavorItemViewHolder favorItemViewHolder = (FavorItemViewHolder) holder;
//            favorItemViewHolder.name.setText(dataList.get(position).getName());
//            favorItemViewHolder.content.setText(dataList.get(position).getContent());
//        }
////        } else if (holder instanceof ButtonViewHolder) {
////            ButtonViewHolder buttonViewHolder = (ButtonViewHolder) holder;
////        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return dataList.size();
//    }
//
//    class FavorItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private onRecycleItemClickListener mListener;
//        private TextView name;
//        private TextView content;
//        private LinearLayout favor_item;
//        private LinearLayout favor_item_head;
//
//        public FavorItemViewHolder(View itemView, FavorItemAdapter.onRecycleItemClickListener listener) {
//            super(itemView);
//            name = (TextView) itemView.findViewById(R.id.spot_name);
//            content = (TextView) itemView.findViewById(R.id.spot_content);
//            favor_item = (LinearLayout) itemView.findViewById(R.id.favor_item);
//            favor_item_head = (LinearLayout) itemView.findViewById(R.id.favor_item_head);
//            mListener = listener;
//            favor_item.setOnClickListener(this);
//            favor_item_head.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            mListener.onItemClick(view, getAdapterPosition());
//        }
//    }
//
////    class ButtonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
////        private onRecycleItemClickListener mListener;
////        private ImageButton button;
////
////        public ButtonViewHolder(View itemView, FavorItemAdapter.onRecycleItemClickListener listener) {
////            super(itemView);
////            button = (ImageButton) itemView.findViewById(R.id.favor_add);
////            mListener = listener;
////            button.setOnClickListener(this);
////        }
////
////        @Override
////        public void onClick(View view) {
////            mListener.onItemClick(view, getAdapterPosition());
////        }
////    }
//
//    public interface onRecycleItemClickListener {
//        void onItemClick(View view, int position);
//    }
//
////    public interface onBtnItemClickListener {
////        void onBtnClick(View view, int position);
////    }
}
