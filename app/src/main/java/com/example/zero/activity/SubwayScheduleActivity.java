package com.example.zero.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zero.adapter.AdvDestinMultiAdapter;
import com.example.zero.adapter.SaleHotCouponAdapter;
import com.example.zero.adapter.ScheduleAdapter;
import com.example.zero.bean.AdvDestinMultiBean;
import com.example.zero.bean.ScheduleBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.HttpUtil;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class SubwayScheduleActivity extends AppCompatActivity {
    private Context context;

    private View spinnerSet;
    private TextView spinner1;
    private TextView spinner2;

    private List<String> choice1;
    private List<String> choice2;
    private ArrayAdapter<String> choiceAdapter1;
    private ArrayAdapter<String> choiceAdapter2;
    private ListPopupWindow popupWindow1;
    private ListPopupWindow popupWindow2;

    private RecyclerView recyclerView;
    private List<ScheduleBean> scheduleData;
    private ScheduleAdapter scheduleAdapter;

    private String stationName;

    private static final String TAG = "SubwayScheduleActivity";

    private ProgressDialog pd;

    //定义Handler对象
    private Handler httpHandler = new Handler(new Handler.Callback() {
        @Override
        //当有消息发送出来的时候就执行Handler的这个方法
        public boolean handleMessage(Message msg) {
            //只要执行到这里就关闭对话框
            pd.dismiss();

            scheduleAdapter.notifyDataSetChanged();
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway_schedule);
        context = getBaseContext();
        initView();
        initData();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) this.findViewById(R.id.station_schedule_recy);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        scheduleData = new ArrayList<ScheduleBean>();
        scheduleAdapter = new ScheduleAdapter(scheduleData);
        recyclerView.setAdapter(scheduleAdapter);

        spinnerSet = (View) this.findViewById(R.id.station_spinner_set);
        spinner1 = (TextView) this.findViewById(R.id.station_spinner1);
        spinner2 = (TextView) this.findViewById(R.id.station_spinner2);

        DisplayMetrics dm = new DisplayMetrics();// 获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        final int screenWidth = dm.widthPixels;
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

                switch (choice1.get(i)) {
                    case "一号线":
                        choice2 = Arrays.asList(getResources().getStringArray(R.array.subway_1));
                        choiceAdapter2 = new ArrayAdapter<>(context, R.layout.adv_pop_item, R.id.textViewPop, choice2);
                        popupWindow2.setAdapter(choiceAdapter2);
                        break;
                    case "二号线":
                        choice2 = Arrays.asList(getResources().getStringArray(R.array.subway_2));
                        choiceAdapter2 = new ArrayAdapter<>(context, R.layout.adv_pop_item, R.id.textViewPop, choice2);
                        popupWindow2.setAdapter(choiceAdapter2);
                        break;
                    case "三号线":
                        break;
                    case "三号线(北延段)":
                        break;
                    case "四号线":
                        break;
                    case "五号线":
                        break;
                    case "六号线":
                        break;
                    case "七号线":
                        break;
                    case "八号线":
                        break;
                    case "广佛线":
                        break;
                    case "APM线":
                        break;
                    default:
                        break;
                }
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
                stationName = choice2.get(i);
                spinner2.setText(choice2.get(i));
                popupWindow2.dismiss();

                httpThread();
            }
        });
    }

    private void httpThread() {
        //构建一个下载进度条
        pd = ProgressDialog.show(SubwayScheduleActivity.this, "加载数据", "数据加载中，请稍后......");

        new Thread() {
            @Override
            public void run() {
                //在新线程里执行长耗时方法
                longTimeMethod();
                //执行完毕后给handler发送一个空消息
                httpHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    //加载数据
    private void longTimeMethod() {
        try {
            final Bundle mBundle = new Bundle();

            mBundle.putString("station", stationName);
            HttpUtil.sendScheduleFullOkHttpRequest(mBundle, new okhttp3.Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onFailure: ERROR!");
                    Toast.makeText(context, "连接服务器失败，请重新尝试！", Toast.LENGTH_LONG).show();
                }
            });
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        choice1 = Arrays.asList(getResources().getStringArray(R.array.subway_lines));
        choice2 = Arrays.asList(getResources().getStringArray(R.array.prefers));
        choiceAdapter1 = new ArrayAdapter<>(this, R.layout.adv_pop_item, R.id.textViewPop, choice1);
        choiceAdapter2 = new ArrayAdapter<>(this, R.layout.adv_pop_item, R.id.textViewPop, choice2);
        popupWindow1.setAdapter(choiceAdapter1);
        popupWindow2.setAdapter(choiceAdapter2);
    }

    private void parseJSONWithJSONObject(String jsonData) {
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray array = jsonArray.getJSONArray(i);
                for (int j = 0; j < array.length(); j++) {
                    scheduleData.add(new ScheduleBean(array.getJSONObject(j).getString("station"),
                            array.getJSONObject(j).getString("line"),
                            array.getJSONObject(j).getString("final_st"),
                            array.getJSONObject(j).getString("arr_time_str")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
