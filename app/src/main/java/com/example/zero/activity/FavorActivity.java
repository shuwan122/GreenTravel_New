package com.example.zero.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.zero.adapter.FavorItemAdapter;
import com.example.zero.bean.FavorItemBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.view.RecycleItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jojo on 2017/10/10.
 */

public class FavorActivity extends AppCompatActivity {
    private ImageView backArrow;
    private RadioButton favor_spot, favor_route;
    private RadioGroup favor_rg;
    private TextView spot_name;
    private TextView spot_content;
    private RecyclerView favor_recv;
    private List<FavorItemBean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);
        innitView();
        showData();
        /**
         *  监听器
         */
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        favor_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.favor_spot:
                    case R.id.favor_route:
                }
            }
        });
    }

    public void innitView() {
        backArrow = (ImageView) findViewById(R.id.favor_back_arrow);
        favor_rg = (RadioGroup) findViewById(R.id.favor_rg);
        favor_spot = (RadioButton) findViewById(R.id.favor_spot);
        favor_route = (RadioButton) findViewById(R.id.favor_route);
        favor_recv = (RecyclerView) findViewById(R.id.favor_recv);
        favor_recv.setLayoutManager(new LinearLayoutManager(this));
        spot_name = (TextView) findViewById(R.id.spot_name);
        spot_content = (TextView) findViewById(R.id.spot_content);
    }

    private void showData() {
        FavorItemBean favorItemBean = new FavorItemBean();
        favorItemBean.setText("北京西站", "出行：3路；9路；21路");
        for (int i = 0; i < 20; i++) {
            dataList.add(favorItemBean);
        }
        FavorItemAdapter adapter = new FavorItemAdapter(this, dataList);
        favor_recv.setAdapter(adapter);
        favor_recv.addItemDecoration(new RecycleItemDecoration(this, RecycleItemDecoration.VERTICAL_LIST));
    }
}
