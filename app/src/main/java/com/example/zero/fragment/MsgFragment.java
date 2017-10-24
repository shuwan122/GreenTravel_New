package com.example.zero.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zero.adapter.MsgAdapter;
import com.example.zero.bean.MsgBean;
import com.example.zero.greentravel_new.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息Frag
 */
public class MsgFragment extends Fragment {
    private View msg_frag;
    private RecyclerView msg_recv;
    private List<MsgBean> dataList = new ArrayList<>();
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        msg_frag = inflater.inflate(R.layout.fragment_msg, container, false);
        innitView();
        showData();
        context = msg_frag.getContext();
        return msg_frag;
    }

    private void innitView() {
        msg_recv = (RecyclerView) msg_frag.findViewById(R.id.msg_recv);
        msg_recv.setLayoutManager(new LinearLayoutManager(context));
    }

    private void showData() {
        MsgBean msgBean = new MsgBean();
        msgBean.setText("肯德基超值优惠", "国庆放价来袭，让你抢不停。", "17/09/20", R.drawable.kfc);
        for (int i = 0; i < 5; i++) {
            dataList.add(msgBean);
        }
        MsgAdapter adapter = new MsgAdapter(msg_frag.getContext(), dataList);
        msg_recv.setAdapter(adapter);
    }
}
