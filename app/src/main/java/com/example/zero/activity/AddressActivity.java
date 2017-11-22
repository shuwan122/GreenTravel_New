package com.example.zero.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.zero.adapter.AddressAdapter;
import com.example.zero.bean.AddressBean;
import com.example.zero.greentravel_new.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jojo on 2017/11/15.
 */

public class AddressActivity extends AppCompatActivity {

    private RecyclerView addr_recy;
    private List<AddressBean> dataList = new ArrayList<>();
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initView();
        showAddress();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void initView(){
        addr_recy = (RecyclerView) findViewById(R.id.address_recy);
        addr_recy.setLayoutManager(new LinearLayoutManager(this));
        back = (ImageView) findViewById(R.id.address_back_arrow);
    }

    public void showAddress(){
        AddressBean addressBean = new AddressBean();
        addressBean.setName("吴姿颍");
        addressBean.setPhone("123456789");
        addressBean.setAddress("北京市海淀区西土城路10号北京邮电大学学生公寓11");
        dataList.add(addressBean);
        dataList.add(addressBean);
        dataList.add(addressBean);
        dataList.add(addressBean);
        dataList.add(addressBean);
        dataList.add(addressBean);
        AddressAdapter addressAdapter = new AddressAdapter(this, dataList);
        addr_recy.setAdapter(addressAdapter);
        addressAdapter.setOnItemClickListener(new AddressAdapter.onRecycleItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(AddressActivity.this, AddressEditActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItem1Click(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(AddressActivity.this, AddressEditActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItem2Click(View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
                AlertDialog alertDialog = builder.setMessage("确定要删除该地址吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                alertDialog.show();
            }
        });
    }
}
