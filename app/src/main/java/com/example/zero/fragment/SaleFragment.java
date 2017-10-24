package com.example.zero.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.zero.greentravel_new.R;

/**
 * SaleFragment
 */

public class SaleFragment extends Fragment {

    private RadioGroup saleGroup;
    private SaleFragmentController controller;
    private View hot, my;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        hot = (View) view.findViewById(R.id.sale_hot_line);
        my = (View) view.findViewById(R.id.sale_my_line);
        controller = SaleFragmentController.getInstance(this, R.id.sale_frag_content);
        controller.showFragment(0);
        saleGroup = (RadioGroup) view.findViewById(R.id.sale_frag_rg);
        saleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.sale_hot:
                        controller.showFragment(0);
                        hot.setBackgroundColor(getResources().getColor(R.color.red, null));
                        my.setBackgroundColor(getResources().getColor(R.color.white, null));
                        break;
                    case R.id.sale_my:
                        controller.showFragment(1);
                        my.setBackgroundColor(getResources().getColor(R.color.red, null));
                        hot.setBackgroundColor(getResources().getColor(R.color.white, null));
                    default:
                        break;
                }
            }
        });
        return view;
    }
}
