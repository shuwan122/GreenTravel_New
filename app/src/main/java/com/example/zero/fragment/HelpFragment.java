package com.example.zero.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jojo on 2017/10/16.
 */

public class HelpFragment extends Fragment {
    private String TAG = "HelpFragment";
    private View helpFrag;
    private Context context;
    private TextView text1, text2, text3, text4, text5, text6, text7;
    private List<String> id = new ArrayList<>();
    private List<String> question = new ArrayList<>();
    private List<String> answer = new ArrayList<>();
    private LinearLayout ask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        helpFrag = inflater.inflate(R.layout.fragment_help, container, false);
        innitView();
        context = helpFrag.getContext();
        getHotQuestion();
        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return helpFrag;
    }

    public void innitView() {
        text1 = (TextView) helpFrag.findViewById(R.id.q1_text);
        text2 = (TextView) helpFrag.findViewById(R.id.q2_text);
        text3 = (TextView) helpFrag.findViewById(R.id.q3_text);
        text4 = (TextView) helpFrag.findViewById(R.id.q4_text);
        text5 = (TextView) helpFrag.findViewById(R.id.q5_text);
        text6 = (TextView) helpFrag.findViewById(R.id.q6_text);
        text7 = (TextView) helpFrag.findViewById(R.id.q7_text);
        ask = (LinearLayout)helpFrag.findViewById(R.id.ask_question);
    }

    public void getHotQuestion() {
        HashMap<String, String> params = new HashMap<>();
        RequestManager.getInstance(context).requestAsyn("users/get_q_and_a", RequestManager.TYPE_GET, params, new RequestManager.ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("q_and_a"));
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jo = JSON.parseObject(jsonArray.get(i).toString());
                    id.add(jo.getString("id"));
                    question.add(jo.getString("question"));
                    answer.add(jo.getString("answer"));
                }
                text1.setText(id.get(0)+". "+question.get(0));
                text2.setText(id.get(1)+". "+question.get(1));
                text3.setText(id.get(2)+". "+question.get(2));
            }

            @Override
            public void onReqFailed(String errorMsg) {
                Log.e(TAG,errorMsg);
            }
        });
    }
}
