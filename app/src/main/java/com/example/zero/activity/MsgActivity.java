package com.example.zero.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.zero.adapter.MsgAdapter;
import com.example.zero.bean.MsgBean;
import com.example.zero.greentravel_new.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息activity
 */
public class MsgActivity extends AppCompatActivity {
    private RecyclerView msg_recv;
    private ImageView backArrow;
    private List<MsgBean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        innitView();
        showData();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void innitView() {
        msg_recv = (RecyclerView) findViewById(R.id.msg_recv);
        msg_recv.setLayoutManager(new LinearLayoutManager(this));
        backArrow = (ImageView) findViewById(R.id.msg_back_arrow);
    }

    private void showData() {
        MsgBean msgBean = new MsgBean();
        msgBean.setText("肯德基超值优惠", "国庆放价来袭，让你抢不停。", "17/09/20", R.drawable.kfc);
        for (int i = 0; i < 5; i++) {
            dataList.add(msgBean);
        }
        MsgAdapter adapter = new MsgAdapter(this, dataList);
        msg_recv.setAdapter(adapter);
    }
}
