package com.example.zero.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zero.activity.FavorActivity;
import com.example.zero.activity.FriendActivity;
import com.example.zero.activity.HelpFeedbackActivity;
import com.example.zero.activity.LoginActivity;
import com.example.zero.activity.MsgActivity;
import com.example.zero.activity.RegisterActivity;
import com.example.zero.activity.SettingActivity;
import com.example.zero.activity.UserActivity;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.MainApplication;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * @author
 * Created by jojo on 2017/9/22.
 */

public class PersonalInfoFragment extends Fragment {
    private View person_frag;
    private Context context;
    private TextView setting;
    private TextView login;
    private TextView register;
    private TextView user_name;
    private RoundedImageView img;
    private LinearLayout msg;
    private LinearLayout user;
    private LinearLayout favor;
    private LinearLayout friend;
    private LinearLayout help_feedback;

    private static final int START_LOGIN_ACTIVITY = 1;
    private static final int START_REGISTER_ACTIVITY = 2;
    private static final int START_USER_ACTIVITY = 3;
    private static final String TAG = "PersonalInfoFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        person_frag = inflater.inflate(R.layout.fragment_personal_info, container, false);
        context = getContext();
        innitView();
        Glide.with(getContext())
                .load("http://10.108.120.91:8080/users/XkF171031150359171103175428.jpg?type=1")
                .placeholder(R.drawable.personal_img)
                .into(img);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SettingActivity.class);
                startActivityForResult(intent,START_USER_ACTIVITY);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainApplication mainApplication = (MainApplication) getActivity().getApplication();
                if(mainApplication.isOnline()) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), UserActivity.class);
                    startActivity(intent);
                    Log.d(TAG,"online");
                }
                else {
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"offline");
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivityForResult(intent, START_LOGIN_ACTIVITY);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), RegisterActivity.class);
                startActivityForResult(intent, START_REGISTER_ACTIVITY);
            }
        });
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MsgActivity.class);
                startActivity(intent);
            }
        });
        favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FavorActivity.class);
                startActivity(intent);
            }
        });
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FriendActivity.class);
                startActivity(intent);
            }
        });
        help_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), HelpFeedbackActivity.class);
                startActivity(intent);
            }
        });
        context = person_frag.getContext();

        return person_frag;
    }

    public void innitView() {
        setting = (TextView) person_frag.findViewById(R.id.setting);
        user = (LinearLayout) person_frag.findViewById(R.id.user_info);
        login = (TextView) person_frag.findViewById(R.id.login);
        register = (TextView) person_frag.findViewById(R.id.register);
        user_name = (TextView) person_frag.findViewById(R.id.user_name);
        img = (RoundedImageView) person_frag.findViewById(R.id.user_img);
        msg = (LinearLayout) person_frag.findViewById(R.id.msg);
        favor = (LinearLayout) person_frag.findViewById(R.id.favor);
        friend = (LinearLayout) person_frag.findViewById(R.id.friends);
        help_feedback = (LinearLayout) person_frag.findViewById(R.id.help_feedback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case START_LOGIN_ACTIVITY:
                    OnRefreshOnlineState();
                    break;
                case START_REGISTER_ACTIVITY:
                    OnRefreshOnlineState();
                    break;
                case START_USER_ACTIVITY:
                    OnRefreshOnlineState();
                    break;
                default:OnRefreshOnlineState();
            }
        }
    }

    public void OnRefreshOnlineState() {
        MainApplication mainApplication = (MainApplication) getActivity().getApplication();
        if(mainApplication.isOnline()) {
            user_name.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
            register.setVisibility(View.GONE);
            user_name.setText(mainApplication.getUsername());
            String avator = mainApplication.getAvator();
            if(avator!=null&&!avator.equals("")) {
                Glide.with(getContext())
                        .load(avator)
                        .dontAnimate()
                        .placeholder(R.drawable.personal_img)
                        .into(img);
            }
        }
        else {
            user_name.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidd) {
        if (!hidd) {
            OnRefreshOnlineState();
        }
    }
}