package com.example.zero.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.labels.LabelsView;
import com.example.zero.adapter.AdvDestinMultiAdapter;
import com.example.zero.adapter.AdvDestinSearchAdapter;
import com.example.zero.bean.AdvDestinMultiBean;
import com.example.zero.bean.AdvDestinSearchBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by kazu_0122 on 2017/9/22.
 */

public class AdvSearchActivity extends AppCompatActivity {
    private View spinnerSet;
    private TextView textView;
    private TextView spinner1;
    private TextView spinner2;
    private TextView spinner3;
    private List<String> choice1;
    private List<String> choice2;
    private ArrayList<AdvDestinMultiBean> choice3;
    private ArrayAdapter<String> choiceAdapter1;
    private ArrayAdapter<String> choiceAdapter2;
    private AdvDestinMultiAdapter choiceAdapter3;
    private RecyclerView adv_recv;
    private List<AdvDestinSearchBean> dataList = new ArrayList<>();
    private List<AdvDestinSearchBean> showList = new ArrayList<>();
    private Map<String,Boolean> toggleState;
    private ListPopupWindow popupWindow1;
    private ListPopupWindow popupWindow2;
    private ListPopupWindow popupWindow3;

    private Context context;

    private static final String TAG = "AdvSearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = getBaseContext();
        setContentView(R.layout.activity_adv_search);
        initView();
        initData();
        showData();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

    }

    private void initView() {
        Intent intent = getIntent();
        String pos = intent.getStringExtra("position");
        String keys = intent.getStringExtra("keywords");
        textView = (TextView) this.findViewById(R.id.adv_act);
        textView.setText(pos + "  -  " + keys);
        adv_recv = (RecyclerView) this.findViewById(R.id.adv_search_recv);
        adv_recv.setLayoutManager(new LinearLayoutManager(context));

        spinnerSet = (View) this.findViewById(R.id.adv_spinner_set);
        spinner1 = (TextView) this.findViewById(R.id.adv_spinner1);
        spinner2 = (TextView) this.findViewById(R.id.adv_spinner2);
        spinner3 = (TextView) this.findViewById(R.id.adv_spinner3);

        DisplayMetrics dm = new DisplayMetrics();// 获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        popupWindow1 = new ListPopupWindow(this);
        popupWindow1.setAnchorView(spinnerSet);
        popupWindow1.setVerticalOffset(1);
        popupWindow1.setWidth(screenWidth);
        popupWindow1.setHeight(500);
        popupWindow1.setModal(true);
        spinner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow1.show();
            }
        });
        popupWindow1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //TODO 排序更新
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                spinner1.setText(choice1.get(i));
                popupWindow1.dismiss();
            }
        });

        popupWindow2 = new ListPopupWindow(this);
        popupWindow2.setAnchorView(spinnerSet);
        popupWindow2.setVerticalOffset(1);
        popupWindow2.setWidth(screenWidth);
        popupWindow2.setHeight(500);
        popupWindow2.setModal(true);
        spinner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow2.show();
            }
        });
        popupWindow2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //TODO 排序更新
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                spinner2.setText(choice2.get(i));
                popupWindow2.dismiss();
            }
        });

        popupWindow3 = new ListPopupWindow(this);
        popupWindow3.setAnchorView(spinnerSet);
        popupWindow3.setVerticalOffset(1);
        popupWindow3.setWidth(screenWidth);
        popupWindow3.setHeight(500);
        popupWindow3.setModal(true);
        spinner3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow3.show();
            }
        });

        popupWindow3.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //TODO 多标签搜索更新
                String s = "";
                for(int i = 0;i < 3;i++) {
                    LabelsView labelsView = popupWindow3.getListView().getChildAt(i).findViewById(R.id.labelsViewCheck);
                    choice3.get(i).setSelected(labelsView.getSelectLabels());
                }
                Toast.makeText(getBaseContext(),"dismiss"+s,Toast.LENGTH_LONG).show();
            }
        });

    }

    private void showData() {
        ArrayList labels = new ArrayList<String>();
        labels.add("快捷");
        labels.add("便宜");
        labels.add("凯德广场");
        labels.add("学生党");
        AdvDestinSearchBean searchBean1 = new AdvDestinSearchBean();
        AdvDestinSearchBean searchBean2 = new AdvDestinSearchBean();
        searchBean1.setText(false,"肯德基","肯德基", 900, 50, "快餐  129米",
                R.drawable.kfc, (float) 4.3, labels);
        searchBean2.setText(false,"麦当劳","麦当劳", 900, 50, "相关商家129家",
                R.drawable.mcdonald, (float) 4.3, labels);
        AdvDestinSearchBean searchBean3 = new AdvDestinSearchBean();
        AdvDestinSearchBean searchBean4 = new AdvDestinSearchBean();
        searchBean3.setText(true,"肯德基","肯德基", 900, 50, "快餐  129米",
                R.drawable.kfc, (float) 4.3, labels);
        searchBean4.setText(true,"麦当劳","麦当劳", 900, 50, "相关商家129家",
                R.drawable.mcdonald, (float) 4.3, labels);
        dataList.add(searchBean3);
        for (int i = 0; i < 10; i++) {
            dataList.add(searchBean1);
        }
        dataList.add(searchBean4);
        for (int i = 0; i < 10; i++) {
            dataList.add(searchBean2);
        }
        showList.add(searchBean3);
        showList.add(searchBean4);
        toggleState = new HashMap<String, Boolean>();
        toggleState.put("肯德基",false);
        toggleState.put("麦当劳",false);
        final AdvDestinSearchAdapter adapter1 = new AdvDestinSearchAdapter(this,showList);
        adv_recv.setAdapter(adapter1);
        adapter1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(showList.get(position).isStation()) {
                    String s = showList.get(position).getStationTag();
                    toggleState.put(s,!toggleState.get(s));
                    Toast.makeText(getBaseContext(),position+"yes  "+s,Toast.LENGTH_SHORT).show();
                    showList.clear();
                    for(AdvDestinSearchBean bean : dataList) {
                        if(bean.isStation()) {
                            showList.add(bean);
                            bean.setToggle(toggleState.get(bean.getStationTag()));
                        }
                        else if(toggleState.get(bean.getStationTag())) {
                            showList.add(bean);
                        }
                    }
                    adapter1.notifyDataSetChanged();
                }
                else Toast.makeText(getBaseContext(),position+"no",Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    private void initData() {
        choice1 = Arrays.asList(getResources().getStringArray(R.array.blocks));
        choice2 = Arrays.asList(getResources().getStringArray(R.array.prefers));
        choice3 = new ArrayList<>();
        ArrayList<String> sub1 = new ArrayList<>();
        sub1.add("预定");
        sub1.add("排队");
        sub1.add("点菜");
        sub1.add("外卖");
        ArrayList<String> sub2 = new ArrayList<>();
        sub2.add("50以下");
        sub2.add("50-100");
        sub2.add("100-300");
        sub2.add("300以上");
        ArrayList<String> sub3 = new ArrayList<>();
        sub3.add("新店");
        sub3.add("WiFi");
        sub3.add("团购");
        sub3.add("营业中");
        choice3.add(new AdvDestinMultiBean("价格",true,new ArrayList<Integer>(),sub2));
        choice3.add(new AdvDestinMultiBean("服务",false,new ArrayList<Integer>(),sub1));
        choice3.add(new AdvDestinMultiBean("更多",false,new ArrayList<Integer>(),sub3));
        choiceAdapter1 = new ArrayAdapter<>(this, R.layout.adv_pop_item, R.id.textViewPop, choice1);
        choiceAdapter2 = new ArrayAdapter<>(this, R.layout.adv_pop_item, R.id.textViewPop,choice2);
        choiceAdapter3 = new AdvDestinMultiAdapter(choice3,this);
        popupWindow1.setAdapter(choiceAdapter1);
        popupWindow2.setAdapter(choiceAdapter2);
        popupWindow3.setAdapter(choiceAdapter3);
    }

}
