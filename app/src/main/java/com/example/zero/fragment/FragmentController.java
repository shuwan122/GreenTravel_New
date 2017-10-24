package com.example.zero.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

public class FragmentController {

    /**
     * 容器
     */
    private int containerId;
    /**
     * RouteFragment管理
     */
    private FragmentManager fm;
    /**
     * RouteFragment列表
     */
    private ArrayList<Fragment> fragments;

    /**
     * RouteFragment控制器类
     */
    private static FragmentController controller;

    public static FragmentController getInstance(FragmentActivity activity, int containerId) {
        if (controller == null) {
            controller = new FragmentController(activity, containerId);
        }
        return controller;
    }

    private FragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragments = new ArrayList<Fragment>();
//        fragments.add(new RouteFragment());
        fragments.add(new RouteFragmentDouble());
        fragments.add(new AdvFragment());
        fragments.add(new SaleFragment());
        fragments.add(new PersonalInfoFragment());

        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commit();
    }

    /**
     * 隐藏Fragment
     */
    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    /**
     * 显示Fragment
     * @param position Fragment位置
     */
    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }
}
