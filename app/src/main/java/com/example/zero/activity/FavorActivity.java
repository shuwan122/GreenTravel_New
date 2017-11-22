package com.example.zero.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.example.zero.fragment.FavorFragmentController;
import com.example.zero.greentravel_new.R;

/**
 * Created by jojo on 2017/10/10.
 */

public class FavorActivity extends AppCompatActivity {
    private ImageView backArrow;
    private RadioGroup favor_rg;
    private FavorFragmentController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favor);
        innitView();
        controller.showFragment(0);
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
                    case R.id.favor_sta:
                        controller.showFragment(0);
                        break;
                    case R.id.favor_shop:
                        controller.showFragment(1);
                        break;
                    default:
                        break;
                }
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void innitView() {
        backArrow = (ImageView) findViewById(R.id.favor_back_arrow);
        favor_rg = (RadioGroup) findViewById(R.id.favor_rg);
        controller = FavorFragmentController.getInstance(this, R.id.favor_frag_content);
    }

//    private void showData() {
//        FavorItemBean favorItemBean = new FavorItemBean();
//        favorItemBean.setText("北京西站", "出行：3路；9路；21路");
//        for (int i = 0; i < 20; i++) {
//            dataList.add(favorItemBean);
//            favorItemBean.setType("Item");
//        }
//        FavorItemBean fb = new FavorItemBean();
//        fb.setType("Button");
//        dataList.add(fb);
//    }
}
