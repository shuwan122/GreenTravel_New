package com.example.zero.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.zero.greentravel_new.R;

/**
 * Created by zero on 2017/8/7.
 */

public class AdvFragment extends Fragment {

    private RadioGroup rGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private View view1;
    private View view2;
    private AdvFragmentController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adv, container, false);
        controller = AdvFragmentController.getInstance(this, R.id.adv_frg_content);
        controller.showFragment(0);
        rGroup = (RadioGroup) view.findViewById(R.id.adv_rg);
        rb1 = (RadioButton) view.findViewById(R.id.adv_rb_1);
        rb2 = (RadioButton) view.findViewById(R.id.adv_rb_2);
        view1 = (View) view.findViewById(R.id.adv_color1);
        view2 = (View) view.findViewById(R.id.adv_color2);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.adv_rb_1:
                        controller.showFragment(0);
                        rb1.setTextColor(android.graphics.Color.RED);
                        rb2.setTextColor(getResources().getColor(R.color.nav_gray, null));
                        rb1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.destin_red32, 0, 0, 0);
                        rb2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.plan_gray32, 0, 0, 0);
                        view1.setBackgroundColor(getResources().getColor(R.color.red, null));
                        view2.setBackgroundColor(getResources().getColor(R.color.nav_gray, null));
                        break;
                    case R.id.adv_rb_2:
                        controller.showFragment(1);
                        rb1.setTextColor(getResources().getColor(R.color.nav_gray, null));
                        rb2.setTextColor(android.graphics.Color.RED);
                        rb1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.destin_gray32, 0, 0, 0);
                        rb2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.plan_red32, 0, 0, 0);
                        view1.setBackgroundColor(getResources().getColor(R.color.nav_gray,null));
                        view2.setBackgroundColor(getResources().getColor(R.color.red,null));
                    default:
                        break;
                }
            }
        });

        return view;
    }
}
