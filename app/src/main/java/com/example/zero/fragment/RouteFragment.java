package com.example.zero.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.zero.greentravel_new.R;
import com.example.zero.view.TitleRouteLayout;

/**
 * Created by zero on 2017/8/7.
 */

public class RouteFragment extends Fragment {

    private RadioGroup rGroup;
    private RouteFragmentController controller;
    private FragmentManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        controller = RouteFragmentController.getInstance(this, R.id.route_frg_content);
        manager = getFragmentManager();
        manager.beginTransaction().add(controller.getFragment(0), "single").commit();
        controller.showFragment(0);

        final Button model = (Button) getActivity().findViewById(R.id.title_btn_model);

        final FragmentTransaction mTransaction = manager.beginTransaction();

        model.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //创建弹出式菜单对象
                PopupMenu popup = new PopupMenu(getContext(), view);//第二个参数是绑定的那个view
                //获取菜单填充器
                MenuInflater inflater = popup.getMenuInflater();
                //填充菜单
                inflater.inflate(R.menu.route_model_menu, popup.getMenu());
                //绑定菜单项的点击事件
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.single_model:
                                Toast.makeText(getContext(), "Single", Toast.LENGTH_SHORT).show();
                                model.setText("单人模式");
//                                controller.getFragment(1).onPause();
//                                controller.getFragment(0).onResume();
//                                controller.getFragment(1).setUserVisibleHint(false);
//                                controller.getFragment(0).setUserVisibleHint(true);

                                mTransaction.remove(controller.getFragment(1));
                                mTransaction.add(controller.getFragment(0), "single");
                                mTransaction.commit();
//                                manager.beginTransaction().replace(R.id.LayoutSingle, controller.getFragment(0), "single").commit();

                                controller.showFragment(0);
                                break;
                            case R.id.multi_model:
                                Toast.makeText(getContext(), "Multi", Toast.LENGTH_SHORT).show();
                                model.setText("多人模式");
//                                controller.getFragment(0).onPause();
//                                controller.getFragment(1).onResume();
//                                controller.getFragment(0).setUserVisibleHint(false);
//                                controller.getFragment(1).setUserVisibleHint(true);

                                mTransaction.remove(controller.getFragment(0));
                                mTransaction.add(controller.getFragment(1), "multi");
                                mTransaction.commit();
//                                manager.beginTransaction().replace(R.id.LayoutMulti, controller.getFragment(1), "multi").commit();

                                controller.showFragment(1);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        rGroup = (RadioGroup) view.findViewById(R.id.route_rg);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.route_rb_1:
                        controller.showFragment(0);
                        break;
                    case R.id.route_rb_2:
                        controller.showFragment(1);
                    default:
                        break;
                }
            }
        });
        return view;
    }
}

