package com.example.zero.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

public class RouteFragmentController {

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
    private static RouteFragmentController controller;

    public static RouteFragmentController getInstance(Fragment parentFragment, int containerId) {
        if (controller == null) {
            controller = new RouteFragmentController(parentFragment, containerId);
        }
        return controller;
    }

    private RouteFragmentController(Fragment fragment, int containerId) {
        this.containerId = containerId;
        //fragment嵌套fragment，调用getChildFragmentManager
        fm = fragment.getChildFragmentManager();
        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new RouteSingleFragment());
        fragments.add(new RouteMultiFragment());

        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            ft.add(containerId, fragment);
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

    /**
     * 隐藏Fragment
     */
    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }
}
