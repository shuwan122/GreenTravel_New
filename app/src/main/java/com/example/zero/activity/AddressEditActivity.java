package com.example.zero.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zero.greentravel_new.R;

/**
 * Created by jojo on 2017/11/15.
 */

public class AddressEditActivity extends AppCompatActivity {

    private ImageView backArrow;
    private TextView save;
    private EditText name, phone, area, street, detailed_addr;
    private TextView delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        initView();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();//TODO:判断是否有改动，无改动则直接返回
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddressEditActivity.this);
                AlertDialog alertDialog = builder.setMessage("确定要删除该地址吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                alertDialog.show();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void initView() {
        backArrow = (ImageView) findViewById(R.id.address_edit_back);
        save = (TextView) findViewById(R.id.address_edit_save);
        name = (EditText) findViewById(R.id.address_edit_name);
        phone = (EditText) findViewById(R.id.address_edit_phone);
        area = (EditText) findViewById(R.id.address_edit_area);
        street = (EditText) findViewById(R.id.address_edit_street);
        detailed_addr = (EditText) findViewById(R.id.address_edit_detailed);
        delete = (TextView) findViewById(R.id.address_edit_delete);
    }
}
