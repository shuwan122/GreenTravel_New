package com.example.zero.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * Created by shuwan122 on 2017/9/7.
 */

public class AdvFragmentController {
    /**
     * 容器
     */
    private int containerId;
    /**
     * AdvFragment管理
     */
    private FragmentManager fm;
    /**
     * AdvFragment列表
     */
    private ArrayList<Fragment> fragments;

    /**
     * AdvFragment控制器类
     */
    private static AdvFragmentController controller;

    public static AdvFragmentController getInstance(Fragment parentFragment, int containerId) {
        if (controller == null) {
            controller = new AdvFragmentController(parentFragment, containerId);
        }
        return controller;
    }

    private AdvFragmentController(Fragment fragment, int containerId) {
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
        fragments.add(new AdvDestinFragment());
        fragments.add(new AdvPlanFragment());

        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commit();
    }

    /**
     * 显示Fragment
     *
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
