package com.example.zero.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zero.greentravel_new.R;
import com.example.zero.util.MainApplication;
import com.example.zero.util.RequestManager;

import java.util.HashMap;

/**
 * Created by jojo on 2017/11/15.
 */

public class AddressEditActivity extends AppCompatActivity {

    private String TAG = "AddressEditActivity";
    private TextView backArrow;
    private TextView save;
    private EditText name, phone;
    private String uid;
    private Intent intent;
    private TextWatcher textWatcher;
    private boolean isChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_edit);
        initView();
        name.addTextChangedListener(textWatcher);
        phone.addTextChangedListener(textWatcher);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChanged) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddressEditActivity.this);
                    AlertDialog alertDialog = builder.setMessage("保存已修改的信息吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    saveChange();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }).create();
                    alertDialog.show();
                } else {
                    finish();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChange();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /**
     * 重写系统back键监听事件
     */
    @Override
    public void onBackPressed() {
        if (isChanged) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddressEditActivity.this);
            AlertDialog alertDialog = builder.setMessage("保存已修改的信息吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            saveChange();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).create();
            alertDialog.show();
        } else {
            finish();
        }
    }

    private void initView() {
        intent = getIntent();
        backArrow = (TextView) findViewById(R.id.address_edit_back);
        save = (TextView) findViewById(R.id.address_edit_save);
        name = (EditText) findViewById(R.id.address_edit_name);
        phone = (EditText) findViewById(R.id.address_edit_phone);
        name.setText(intent.getStringExtra("name"));
        name.requestFocus();
        phone.setText(intent.getStringExtra("phone"));
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "beforeTextChanged--------------->");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "onTextChanged--------------->");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isChanged = true;
            }
        };
    }

    private void saveChange() {
        if (intent.getStringExtra("type").equals("add")) {
            Intent intent1 = new Intent();
            intent1.putExtra("is_default", intent.getStringExtra("is_default"));
            intent1.putExtra("name", name.getText().toString());
            intent1.putExtra("phone", phone.getText().toString());
            setResult(1, intent1);
            finish();
        } else if (intent.getStringExtra("type").equals("edit")) {
            MainApplication application = (MainApplication) getApplication();
            uid = application.getUser_id();
            HashMap<String, String> params = new HashMap<>();
            params.put("id", intent.getStringExtra("id"));
            params.put("user_id", uid);
            params.put("is_default", intent.getStringExtra("is_default"));
            params.put("mailing_name", name.getText().toString());
            params.put("mailing_phone", phone.getText().toString());
            RequestManager.getInstance(AddressEditActivity.this).requestAsyn("users/update_mailing_info", RequestManager.TYPE_POST_JSON, params, new RequestManager.ReqCallBack<String>() {
                @Override
                public void onReqSuccess(String result) {
                    Intent intent1 = new Intent();
                    intent1.putExtra("name", name.getText().toString());
                    intent1.putExtra("phone", phone.getText().toString());
                    setResult(2, intent1);
                    finish();
                }

                @Override
                public void onReqFailed(String errorMsg) {
                    Log.e(TAG, errorMsg);
                    Toast.makeText(AddressEditActivity.this, "内部服务器错误", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
