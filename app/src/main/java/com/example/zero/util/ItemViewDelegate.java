package com.example.zero.util;


/**
 * Created by shuwan on 17/10/19.
 */
public interface ItemViewDelegate<T>
{

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(RecycleViewHolder holder, T t, int position);

}
