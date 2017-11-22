package com.example.zero.adapter;

import android.content.Context;

import com.example.zero.bean.UserOrderBean;
import com.example.zero.delegate.UserOrderActionDelegate;
import com.example.zero.delegate.UserOrderGoodsDelegate;
import com.example.zero.delegate.UserOrderShopDelegate;
import com.example.zero.util.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by kazu_0122 on 2017/11/16.
 */

public class UserOrderAdapter extends MultiItemTypeAdapter<UserOrderBean>{
    public UserOrderAdapter(Context context, List<UserOrderBean> datas) {
        super(context, datas);
        addItemViewDelegate(new UserOrderActionDelegate());
        addItemViewDelegate(new UserOrderGoodsDelegate());
        addItemViewDelegate(new UserOrderShopDelegate());
    }
}
