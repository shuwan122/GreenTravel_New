package com.example.zero.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zero.activity.AdvMapResultActivity;
import com.example.zero.activity.AdvSearchActivity;
import com.example.zero.activity.RouteResultActivity;
import com.example.zero.adapter.RouteSearchAdapter;
import com.example.zero.bean.RouteSearchBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.view.SearchPopView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.zero.greentravel_new.R.id.editText;

/**
 * Created by shuwan122 on 2017/9/7.
 * 出行规划fragment
 */

public class AdvPlanFragment extends Fragment implements SearchPopView.SearchPopViewListener {

    private TextView textView;
    private boolean seted;
    private int hour1;
    private int minute1;
    private int hour2;
    private int minute2;
    private Button searchButton;
    private Button btnChange;

    /**
     * 搜索结果列表view
     */
    private ListView lvResults1;
    private ListView lvResults2;
    /**
     * 搜索view
     */
    private SearchPopView searchPopView1;
    private SearchPopView searchPopView2;
    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;
    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter1;
    private ArrayAdapter<String> autoCompleteAdapter2;
    /**
     * 搜索结果列表adapter
     */
    private RouteSearchAdapter resultAdapter1;
    private RouteSearchAdapter resultAdapter2;
    /**
     * 数据库数据，总数据
     */
    private List<RouteSearchBean> dbData;
    /**
     * 热搜版数据
     */
    private List<String> hintData;
    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData1;
    private List<String> autoCompleteData2;
    /**
     * 搜索结果的数据
     */
    private List<RouteSearchBean> resultData1;
    private List<RouteSearchBean> resultData2;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 4;
    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;

    private static final String TAG = "AdvPlanFragment";

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    public static void setHintSize(int hintSize) {
        AdvPlanFragment.hintSize = hintSize;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        seted = false;
        View view = inflater.inflate(R.layout.fragment_adv_plan, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initData();
        initViews();
        super.onActivityCreated(savedInstanceState);
    }


    /**
     * 初始化视图
     */
    private void initViews() {
        textView = (TextView) getView().findViewById(R.id.plan_search_time);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar mCalendar = Calendar.getInstance();
                showTimeDialog();
            }
        });

        searchPopView1 = (SearchPopView) getActivity().findViewById(R.id.plan_search_start);
        searchPopView1.setAutoCompleteAdapter(autoCompleteAdapter1);
        searchPopView1.setTipsHintAdapter(hintAdapter);
        searchPopView1.setHintText("请输入起点");
        searchPopView1.setSearchPopViewListener(this);

        searchPopView2 = (SearchPopView) getActivity().findViewById(R.id.plan_search_end);
        searchPopView2.setAutoCompleteAdapter(autoCompleteAdapter2);
        searchPopView2.setTipsHintAdapter(hintAdapter);
        searchPopView2.setHintText("请输入终点");
        searchPopView2.setSearchPopViewListener(this);

        btnChange = (Button) getActivity().findViewById(R.id.adv_plan_btn_change);

        lvResults1 = (ListView) getView().findViewById(R.id.plan_search_results1);
        lvResults2 = (ListView) getView().findViewById(R.id.plan_search_results2);
        lvResults1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(), "1-" + position + "", Toast.LENGTH_SHORT).show();
            }
        });
        lvResults2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(), "2-" + position + "", Toast.LENGTH_SHORT).show();
            }
        });

        searchButton = (Button) getView().findViewById(R.id.plan_search_commit);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AdvSearchActivity.class);
                //TODO 具体所需搜索信息
                String beginStation = searchPopView1.getText();
                String endStation = searchPopView2.getText();
                Bundle mBundle = new Bundle();
                mBundle.putString("beginStation", beginStation);
                mBundle.putString("endStation", endStation);
                if ((!beginStation.equals("")) & (!endStation.equals(""))) {
                    Intent intent2 = new Intent(getActivity(), AdvMapResultActivity.class);
                    intent2.putExtras(mBundle);
                    startActivity(intent2);
                } else {
                    Toast.makeText(getActivity(), "搜索框消息不完善，请填充完整后在开始搜索！", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text1 = searchPopView1.getText();
                String text2 = searchPopView2.getText();

                searchPopView1.setJUD(false);
                searchPopView2.setJUD(false);

                searchPopView1.setText(text2);
                searchPopView2.setText(text1);

                if (resultData1 == null) {
                    // 初始化
                    resultData1 = new ArrayList<>();
                } else {
                    resultData1.clear();
                    for (int i = 0; i < dbData.size(); i++) {
                        if (dbData.get(i).getTitle().equals(searchPopView1.getText().trim())) {
                            resultData1.add(dbData.get(i));
                        }
                    }
                }

                if (resultData2 == null) {
                    // 初始化
                    resultData2 = new ArrayList<>();
                } else {
                    resultData2.clear();
                    for (int i = 0; i < dbData.size(); i++) {
                        if (dbData.get(i).getTitle().equals(searchPopView2.getText().trim())) {
                            resultData2.add(dbData.get(i));
                        }
                    }
                }

                if (lvResults1.getAdapter() == null) {
                    //获取搜索数据 设置适配器
                    resultAdapter1.getItem(0).setComments("起点");
                    lvResults1.setAdapter(resultAdapter1);
                } else {
                    //更新搜索数据
                    resultAdapter1.getItem(0).setComments("起点");
                    resultAdapter1.notifyDataSetChanged();
                }
                if (lvResults2.getAdapter() == null) {
                    //获取搜索数据 设置适配器
                    resultAdapter2.getItem(0).setComments("终点");
                    lvResults2.setAdapter(resultAdapter2);
                } else {
                    //更新搜索数据
                    resultAdapter2.getItem(0).setComments("终点");
                    resultAdapter2.notifyDataSetChanged();
                }
                cvSearchBtn();
            }
        });
    }

    private void cvSearchBtn() {
        if ((lvResults1.getVisibility() == View.VISIBLE) && (lvResults2.getVisibility() == View.VISIBLE)) {
            searchButton.setVisibility(View.VISIBLE);
        } else {
            searchButton.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化数据
     */

    private void initData() {
        //从数据库获取数据
        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }

    /**
     * 获取db数据
     */
    private void getDbData() {
        int size = 100;
        dbData = new ArrayList<>(size);
        dbData.add(new RouteSearchBean(R.drawable.title_icon, "北京南站地铁站",
                "周围简介\n热门吃、喝、玩、乐", 99 + ""));
        dbData.add(new RouteSearchBean(R.drawable.title_icon, "北京邮电大学西门",
                "周围简介\n热门吃、喝、玩、乐", 99 + ""));
        dbData.add(new RouteSearchBean(R.drawable.title_icon, "北京大学未名湖",
                "周围简介\n热门吃、喝、玩、乐", 99 + ""));
        for (int i = 0; i < size; i++) {
            dbData.add(new RouteSearchBean(R.drawable.title_icon, "站点" + (i + 1),
                    "周围简介\n热门吃、喝、玩、乐", i * 20 + 2 + ""));
        }
    }

    /**
     * 获取热搜版data和adapter
     */
    private void getHintData() {
        hintData = new ArrayList<>(hintSize);
        hintData.add("北京南站地铁站");
        hintData.add("北京邮电大学西门");
        hintData.add("北京大学未名湖");
        for (int i = 1; i <= hintSize; i++) {
            hintData.add("站点" + i * 10);
        }
        hintAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, hintData);
    }

    /**
     * 获取自动补全data和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData1 == null) {
            //初始化
            autoCompleteData1 = new ArrayList<>(hintSize);
        } else {
            // 根据text获取autodata
            autoCompleteData1.clear();
            for (int i = 0, count = 0; i < dbData.size()
                    && count < hintSize; i++) {
                if (dbData.get(i).getTitle().contains(searchPopView1.getText().trim())) {
                    autoCompleteData1.add(dbData.get(i).getTitle());
                    count++;
                }
            }
        }

        if (autoCompleteData2 == null) {
            //初始化
            autoCompleteData2 = new ArrayList<>(hintSize);
        } else {
            // 根据text获取autodata
            autoCompleteData2.clear();
            for (int i = 0, count = 0; i < dbData.size()
                    && count < hintSize; i++) {
                if (dbData.get(i).getTitle().contains(searchPopView2.getText().trim())) {
                    autoCompleteData2.add(dbData.get(i).getTitle());
                    count++;
                }
            }
        }

        if (autoCompleteAdapter1 == null) {
            autoCompleteAdapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, autoCompleteData1);
        } else {
            if (searchPopView1.hasFocus()) {
                autoCompleteAdapter1.notifyDataSetChanged();
            }
        }

        if (autoCompleteAdapter2 == null) {
            autoCompleteAdapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, autoCompleteData2);
        } else {
            if (searchPopView2.hasFocus()) {
                autoCompleteAdapter2.notifyDataSetChanged();
            }
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData1 == null) {
            // 初始化
            resultData1 = new ArrayList<>();
        } else {
            resultData1.clear();
            for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).getTitle().equals(searchPopView1.getText().trim())) {
                    resultData1.add(dbData.get(i));
                }
            }
        }

        if (resultData2 == null) {
            // 初始化
            resultData2 = new ArrayList<>();
        } else {
            resultData2.clear();
            for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).getTitle().equals(searchPopView2.getText().trim())) {
                    resultData2.add(dbData.get(i));
                }
            }
        }

        if (resultAdapter1 == null) {
            resultAdapter1 = new RouteSearchAdapter(getActivity(), resultData1, R.layout.route_search_item_list);
        }

        if (resultAdapter2 == null) {
            resultAdapter2 = new RouteSearchAdapter(getActivity(), resultData2, R.layout.route_search_item_list);
        }
    }

    /**
     * 当搜索框文本改变时触发的回调 ,更新自动补全数据
     *
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        //更新result数据
        searchPopView1.setJUD(false);
        searchPopView2.setJUD(false);
        getResultData(text);
        if (!searchPopView1.getText().equals("")) {
            lvResults1.setVisibility(View.VISIBLE);
        }
        if (!searchPopView2.getText().equals("")) {
            lvResults2.setVisibility(View.VISIBLE);
        }

        if (searchPopView1.hasFocus()) {
            //第一次获取结果 还未配置适配器
            resultAdapter1.getItem(0).setComments("起点");
            if (lvResults1.getAdapter() == null) {
                //获取搜索数据 设置适配器
                lvResults1.setAdapter(resultAdapter1);
            } else {
                //更新搜索数据
                resultAdapter1.notifyDataSetChanged();
            }
        }

        if (searchPopView2.hasFocus()) {
            resultAdapter2.getItem(0).setComments("终点");
            if (lvResults2.getAdapter() == null) {
                //获取搜索数据 设置适配器
                lvResults2.setAdapter(resultAdapter2);
            } else {
                //更新搜索数据
                resultAdapter2.notifyDataSetChanged();
            }
        }

        cvSearchBtn();

        Toast.makeText(getActivity(), "完成搜索", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBack() {
        searchPopView1.setJUD(true);
        searchPopView2.setJUD(true);
        if (searchPopView1.getText().equals("")) {
            lvResults1.setVisibility(View.GONE);
            autoCompleteAdapter1.notifyDataSetChanged();
            resultAdapter1.notifyDataSetChanged();
        }
        if (searchPopView2.getText().equals("")) {
            lvResults2.setVisibility(View.GONE);
            autoCompleteAdapter2.notifyDataSetChanged();
            resultAdapter2.notifyDataSetChanged();
        }
        hintAdapter.notifyDataSetChanged();
    }

    /**
     * 自动补全提示框出现触发回调
     */
    @Override
    public void isFocus() {
        searchPopView1.setJUD(true);
        searchPopView2.setJUD(true);
        if (searchPopView1.hasFocus()) {
            lvResults1.setVisibility(View.GONE);
            autoCompleteAdapter1.notifyDataSetChanged();
            resultAdapter1.notifyDataSetChanged();
        }
        if (searchPopView2.hasFocus()) {
            lvResults2.setVisibility(View.GONE);
            autoCompleteAdapter2.notifyDataSetChanged();
            resultAdapter2.notifyDataSetChanged();
        }
        hintAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onHintClick(String text) {
        return true;
    }

    /**
     * 获取时间段的
     */
    private void showTimeDialog() {

        final AlertDialog.Builder timeDialog = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View dialogView = inflater.inflate(R.layout.fragment_adv_time_dialog, null);
        final TimePicker timePicker1 = (TimePicker) dialogView.findViewById(R.id.timePicker1);
        timePicker1.setIs24HourView(true);
        final TimePicker timePicker2 = (TimePicker) dialogView.findViewById(R.id.timePicker2);
        timePicker2.setIs24HourView(true);
        if (seted) {
            timePicker1.setHour(hour1);
            timePicker1.setMinute(minute1);
            timePicker2.setHour(hour2);
            timePicker2.setMinute(minute2);
        }
        timeDialog.setTitle("请选择出行时间段");
        timeDialog.setView(dialogView);
        timeDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hour1 = timePicker1.getHour();
                        hour2 = timePicker2.getHour();
                        minute1 = timePicker1.getMinute();
                        minute2 = timePicker2.getMinute();
                        String s = "";
                        s += timePicker1.getHour() + ":";
                        s += timePicker1.getMinute() + " - ";
                        s += timePicker2.getHour() + ":";
                        s += timePicker2.getMinute();
                        textView.setText(s);
                        seted = true;
                    }
                });
        timeDialog.setNegativeButton("关闭", null);
        // 显示
        timeDialog.show();
    }

}
