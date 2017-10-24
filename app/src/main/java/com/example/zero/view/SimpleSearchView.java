package com.example.zero.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zero.greentravel_new.R;


public class SimpleSearchView extends LinearLayout implements View.OnClickListener  {

    /**
     * 输入框
     */
    private EditText etInput;
    /**
     * 删除键
     */
    private ImageView ivDelete;
    /**
     * 返回按钮
     */
    private Button btnBack;
    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * 搜索回调接口
     */
    private SimpleSearchView.SimpleSearchViewListener mListener;

    /**
     * 设置搜索回调接口
     *
     * @param listener 监听者
     */
    public void setSearchViewListener(SimpleSearchView.SimpleSearchViewListener listener) {
        mListener = listener;
    }

    public SimpleSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.route_simple_search, this);

        initViews();
    }

    public void setHintText(String str) {
        etInput.setHint(str);
    }

    /**
     * 初始化View
     */
    private void initViews() {
        etInput = (EditText) findViewById(R.id.route_et_search_simple);
        ivDelete = (ImageView) findViewById(R.id.route_iv_delete_simple);
        btnBack = (Button) findViewById(R.id.route_btn_back_simple);

        etInput.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.route_et_search_simple:
                ivDelete.setVisibility(VISIBLE);
                break;
            case R.id.route_iv_delete_simple:
                etInput.setText("");
                ivDelete.setVisibility(GONE);
                break;
            case R.id.route_btn_back_simple:
                etInput.setText("");
                ivDelete.setVisibility(GONE);
//                ((Activity) mContext).finish();
                break;
            default:
                break;
        }
    }

    /**
     * 返回输入框文本内容
     *
     * @return 搜索栏文本
     */
    public String getText() {
        return etInput.getText().toString();
    }

    /**
     * search view回调方法
     */
    public interface SimpleSearchViewListener {
    }
}
