package com.example.zero.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.zero.activity.HelpFeedbackActivity;

import java.util.ArrayList;

/**
 * Created by jojo on 2017/10/16.
 */

public class HelpFeedbackFragmentController {
    /**
     * 容器
     */
    private int containerId;
    /**
     * HelpFeedbackFragment管理
     */
    private FragmentManager fm;
    /**
     * HelpFeedbackFragment列表
     */
    private ArrayList<Fragment> fragments;

    /**
     * HelpFeedbackFragment控制器类
     */
    private static HelpFeedbackFragmentController controller;

    public static HelpFeedbackFragmentController getInstance(HelpFeedbackActivity activity, int containerId) {
        if (controller == null) {
            controller = new HelpFeedbackFragmentController(activity, containerId);
        }
        return controller;
    }

    private HelpFeedbackFragmentController(HelpFeedbackActivity fragment, int containerId) {
        this.containerId = containerId;
        fm = fragment.getSupportFragmentManager();
        initFragment();
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new HelpFragment());
        fragments.add(new FeedbackFragment());

        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
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
