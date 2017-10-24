package com.example.zero.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.example.zero.greentravel_new.R;

public class RouteDrawerActivity extends ActionBarActivity {
    private ImageView img;
    private SlidingDrawer slidingDrawer;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_drawer);
    }
}
