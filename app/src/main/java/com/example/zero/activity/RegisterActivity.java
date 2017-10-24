package com.example.zero.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zero.greentravel_new.R;
import com.example.zero.view.SimpleTextView;


/**
 * Created by jojo on 2017/10/11.
 */

public class RegisterActivity extends AppCompatActivity {
    private ImageView backArrow;
    private Button register, confirm_button;
    private SimpleTextView phone, password, pw_confirm, confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        innitView();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean b) {
                if (!b) {
                    String text = phone.getText().toString().trim();
                    if (!isEmail(text) && !isMobile(text)) {
                        Toast.makeText(RegisterActivity.this, "请输入正确的手机号或邮箱。", Toast.LENGTH_SHORT).show();
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
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (password.getText().toString().trim().length() < 6) {
                        Toast.makeText(RegisterActivity.this, "密码不能少于6个字符。", Toast.LENGTH_SHORT).show();
                    }
                    password.notFocused();
                }
            }
        });
        pw_confirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    if (!password.getText().toString().trim().equals(pw_confirm.getText().toString().trim())) {
                        Toast.makeText(RegisterActivity.this, "两次输入密码不一致。", Toast.LENGTH_SHORT).show();
                    }
                    pw_confirm.notFocused();
                }
            }
        });
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 发送验证码
                String text = phone.getText().toString().trim();
                if (isEmail(text)) {
                    Toast.makeText(RegisterActivity.this, "已发送邮件", Toast.LENGTH_SHORT).show();
                } else if (isMobile(text)) {
                    Toast.makeText(RegisterActivity.this, "已发送短信", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号或邮箱。", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 校验写入用户名密码 验证码
                Toast.makeText(RegisterActivity.this, "注册成功。", Toast.LENGTH_SHORT).show();
                Intent result = new Intent();
                result.putExtra("User name", "name from register");
                setResult(RESULT_OK, result);
                finish();
            }
        });
    }

    public void innitView() {
        backArrow = (ImageView) findViewById(R.id.register_back_arrow);
        phone = (SimpleTextView) findViewById(R.id.register_phone);
        phone.setHintText(" 请输入手机号码/邮箱");
        phone.setLeftImage(R.drawable.user_fill);
        confirm = (SimpleTextView) findViewById(R.id.register_confirm);
        confirm.setHintText(" 验证码");
        confirm.setLeftImage(R.drawable.identify);
        password = (SimpleTextView) findViewById(R.id.register_pw);
        password.setHintText(" 请输入密码");
        password.setLeftImage(R.drawable.lock_fill);
        pw_confirm = (SimpleTextView) findViewById(R.id.register_pw_confirm);
        pw_confirm.setHintText(" 确认密码");
        pw_confirm.setLeftImage(R.drawable.lock_fill);
        confirm_button = (Button) findViewById(R.id.register_confirm_button);
        register = (Button) findViewById(R.id.register_button);

    }

    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String num = "[1][358]\\d{9}";
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    //邮箱验证
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }
}

