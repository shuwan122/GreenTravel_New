package com.example.zero.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zero.greentravel_new.R;

/**
 * Created by jojo on 2017/11/3.
 */

public class FavorShopFragment extends Fragment {
    private View shop_frag;
    private Context context;
    private RecyclerView shop_recy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        shop_frag = inflater.inflate(R.layout.fragment_shop_collect, container, false);
        context = shop_frag.getContext();
        innitView();
        return shop_frag;
    }

    private void innitView(){
        shop_recy = (RecyclerView) shop_frag.findViewById(R.id.shop_collect);
        shop_recy.setLayoutManager(new LinearLayoutManager(context));
    }
}
