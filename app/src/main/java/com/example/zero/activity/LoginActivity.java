package com.example.zero.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zero.greentravel_new.R;
import com.example.zero.view.SimpleTextView;

/**
 * Created by jojo on 2017/10/10.
 */

public class LoginActivity extends AppCompatActivity {
    private ImageView backArrow;
    private TextView register;
    private SimpleTextView phone, confirm;
    private Button confirm_btn, login;

    private static final int START_REGISTER_ACTIVITY = 3;
    private static final int START_FINDPW_ACTIVITY = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        innitView();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, START_REGISTER_ACTIVITY);

            }
        });
        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean b) {

                if (!b) {
                    String text = phone.getText().toString().trim();
                    if (!RegisterActivity.isEmail(text) && !RegisterActivity.isMobile(text)) {
                        Toast.makeText(LoginActivity.this, "请输入正确的手机号或邮箱。", Toast.LENGTH_SHORT).show();
                    }
                    phone.notFocused();
                }
            }
        });
        confirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    confirm.notFocused();
                }
            }
        });
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 发送验证码
                String text = phone.getText().toString().trim();
                if (RegisterActivity.isEmail(text)) {
                    Toast.makeText(LoginActivity.this, "已发送邮件", Toast.LENGTH_SHORT).show();
                } else if (RegisterActivity.isMobile(text)) {
                    Toast.makeText(LoginActivity.this, "已发送短信", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号或邮箱。", Toast.LENGTH_SHORT).show();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                Intent result = new Intent();
                result.putExtra("User name", "name from login");
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }

    public void innitView() {
        backArrow = (ImageView) findViewById(R.id.login_back_arrow);
        register = (TextView) findViewById(R.id.register_text);
        phone = (SimpleTextView) findViewById(R.id.login_phone);
        phone.setHintText(" 请输入手机号码/邮箱");
        phone.setLeftImage(R.drawable.user_fill);
        confirm = (SimpleTextView) findViewById(R.id.login_confirm);
        confirm.setHintText(" 验证码");
        confirm.setLeftImage(R.drawable.identify);
        confirm_btn = (Button) findViewById(R.id.login_confirm_button);
        login = (Button) findViewById(R.id.login_button);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case START_REGISTER_ACTIVITY:
                    String s = data.getStringExtra("User name");
                    Intent result = new Intent();
                    result.putExtra("User name", s);
                    setResult(RESULT_OK, result);
                    finish();
                    break;
                case START_FINDPW_ACTIVITY:
                    break;
            }
        }
    }
}
