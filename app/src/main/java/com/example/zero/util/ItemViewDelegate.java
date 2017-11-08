package com.example.zero.util;


import android.content.Context;

/**
 * Created by shuwan on 17/10/19.
 */
public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(Context context, RecycleViewHolder holder, T t, int position);

}
