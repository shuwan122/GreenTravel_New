package com.example.zero.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zero.greentravel_new.R;
import com.example.zero.util.ViewHolder;

/**
 * Created by jojo on 2017/11/20.
 */

public class BottomPopwindow extends PopupWindow {

    private Context mContext;
    private View view;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2;
    private LinearLayout cancel;
    private View other;

    public BottomPopwindow(Context mContext) {

        this.view = LayoutInflater.from(mContext).inflate(R.layout.popwindow_order_deliver, null);
        radioGroup = (RadioGroup) view.findViewById(R.id.popwindow_rg);
        rb1 = (RadioButton) view.findViewById(R.id.popwindow_rb1);
        rb2 = (RadioButton) view.findViewById(R.id.popwindow_rb2);
        cancel = (LinearLayout) view.findViewById(R.id.popwindow_close);
        other = (View) view.findViewById(R.id.popwindow_other);
        // 取消按钮
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.popwindow_rb1:
                        break;
                    case R.id.popwindow_rb2:
                        break;
                    default:
                        break;
                }
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.bottom_popwindow);
    }
}
