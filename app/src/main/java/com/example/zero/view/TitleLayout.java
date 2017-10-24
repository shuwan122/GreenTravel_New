package com.example.zero.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zero.greentravel_new.R;

/**
 * Created by zero on 2017/9/3.
 */

public class TitleLayout extends LinearLayout {
    public TitleLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);

        Button more = (Button) findViewById(R.id.title_btn_more);
        more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "More info", Toast.LENGTH_SHORT).show();
            }
        });
    }
}