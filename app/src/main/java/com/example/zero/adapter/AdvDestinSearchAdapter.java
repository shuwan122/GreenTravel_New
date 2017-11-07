package com.example.zero.adapter;

import android.content.Context;

import com.example.zero.bean.AdvDestinSearchBean;
import com.example.zero.delegate.AdvStationDelegate;
import com.example.zero.delegate.AdvStoreDelegate;
import com.example.zero.util.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by kazu_0122 on 2017/10/19.
 */

public class AdvDestinSearchAdapter extends MultiItemTypeAdapter<AdvDestinSearchBean> {
    public AdvDestinSearchAdapter(Context context, List<AdvDestinSearchBean> datas) {
        super(context, datas);
        addItemViewDelegate(new AdvStoreDelegate());
        addItemViewDelegate(new AdvStationDelegate());
    }
}

