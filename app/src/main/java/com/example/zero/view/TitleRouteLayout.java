package com.example.zero.view;

import android.content.ClipData;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zero.fragment.RouteFragment;
import com.example.zero.fragment.RouteFragmentController;
import com.example.zero.greentravel_new.R;

/**
 * Created by ZERO on 2017/10/19.
 */

public class TitleRouteLayout extends LinearLayout{

    public TitleRouteLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_route, this);

        final Button more = (Button) findViewById(R.id.title_btn_more);

        more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "More info", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
