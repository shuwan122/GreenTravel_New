package com.example.zero.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * Created by jojo on 2017/11/3.
 */

public class FavorFragmentController {
    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;
    private static FavorFragmentController controller;

    public static FavorFragmentController getInstance(FragmentActivity activity, int containerId) {
        if (controller == null) {
            controller = new FavorFragmentController(activity, containerId);
        }
        return controller;
    }

    private FavorFragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new FavorStationFragment());
        fragments.add(new FavorShopFragment());

        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commit();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }
}
