package com.example.zero.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zero.adapter.MsgAdapter;
import com.example.zero.bean.MsgBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.MainApplication;
import com.example.zero.util.OnItemTouchListener;
import com.example.zero.util.RequestManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 消息activity
 */
public class MsgActivity extends AppCompatActivity {
    private String TAG = "MsgActivity";
    private RecyclerView msg_recv;
    private ImageView backArrow;
    private List<MsgBean> dataList = new ArrayList<>();
    private String uid;
    private List<Integer> msg_id = new ArrayList<>();
    private List<String> msg_url = new ArrayList<>();
    private String msg_pic, msg_title, msg_content, msg_time;
    private MsgAdapter adapter;
    private MainApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        application = (MainApplication) getApplication();
        uid = application.getUser_id();
        innitView();
        getMsg();
//        MsgBean msgBean = new MsgBean();
//        msgBean.setText("系统", "恭喜您获得15积分。", "2017-11-04 12:23:35", "http://d.hiphotos.baidu.com/image/pic/item/32fa828ba61ea8d3f6dedfce9e0a304e241f587f.jpg");
//        dataList.add(msgBean);
//        dataList.add(msgBean);
//        dataList.add(msgBean);
        adapter = new MsgAdapter(this, dataList);
        msg_recv.setAdapter(adapter);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        msg_recv.addOnItemTouchListener(new OnItemTouchListener(msg_recv) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Uri uri = Uri.parse(msg_url.get(vh.getAdapterPosition()));
                Log.e("urllll", uri.toString());
                Intent intent = new Intent();
                // intent.setAction("android.intent.action.VIEW");
                // intent.setData(uri);
                intent.setClass(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
                Toast.makeText(MsgActivity.this, "click" + vh.getAdapterPosition(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void innitView() {
        msg_recv = (RecyclerView) findViewById(R.id.msg_recv);
        msg_recv.setLayoutManager(new LinearLayoutManager(this));
        backArrow = (ImageView) findViewById(R.id.msg_back_arrow);
    }

    public void sendNewMsg() {
        if (application.getMsgBtn() == true) {
            Intent intent = new Intent();
            intent.setAction("MSG_SERVICE");
            intent.setPackage("com.example.zero.greentravel_new");
            startService(intent);
        } else {

        }
    }

    public void getMsg() {
        if (uid == null) {
            Toast.makeText(this, "请您先登录再进行查看", Toast.LENGTH_LONG).show();
        } else {
            HashMap<String, String> params = new HashMap<>();
            params.put("user_id", uid);
            params.put("offset", 0 + "");
            params.put("count", 10 + "");
            RequestManager.getInstance(this).requestAsyn("users/get_msgs", RequestManager.TYPE_POST_JSON, params, new RequestManager.ReqCallBack<String>() {
                @Override
                public void onReqSuccess(String result) {
                    dataList.clear();
                    JSONObject jsonObject = JSON.parseObject(result);
                    JSONArray array = JSONArray.parseArray(jsonObject.getString("msgs"));
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject jo = JSON.parseObject(array.getString(i));
                        msg_id.add(jo.getInteger("msg_id"));
                        msg_pic = jo.getString("photo_url");
                        msg_content = jo.getString("content");
                        msg_url.add(jo.getString("url"));
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date d = new Date(jo.getLong("create_time"));
                        msg_time = sdf.format(d);
                        MsgBean msgBean = new MsgBean();
                        msgBean.setText("系统消息", msg_content, msg_time, msg_pic);
                        dataList.add(msgBean);
                    }
                }

                @Override
                public void onReqFailed(String errorMsg) {
                    Log.e(TAG, errorMsg);
                }
            });
        }
    }
}
