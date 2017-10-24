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
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.example.zero.greentravel_new.R;

/**
 * Created by ZERO on 2017/10/19.
 */

public class SearchViewDouble extends LinearLayout{

    /**
     * 搜索栏
     */
    private SearchPopView search1;
    private SearchPopView search2;
    /**
     * 上下文对象
     */
    private Context mContext;/**
     * 搜索回调接口
     */
    private SearchViewDouble mListener;

    /**
     * 设置搜索回调接口
     *
     * @param listener 监听者
     */
    public void setSearchPopViewDoubleListener(SearchViewDouble listener) {
        mListener = listener;
    }

    public SearchViewDouble(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.route_search_double, this);
        initViews();
    }

    private void initViews(){
        SearchPopView search1 = (SearchPopView) findViewById(R.id.route_search11);
        SearchPopView search2 = (SearchPopView) findViewById(R.id.route_search22);

    }

    public void setHintText1(String str){
        search1.setHintText(str);
    }

    public String getText2(){
        return search2.getText();
    }

    public void setHintText2(String str){
        search2.setHintText(str);
    }

    public String getText1(){
        return search1.getText();
    }

    public void setTipsHintAdapter1(ArrayAdapter<String> hintAdapter){
        search1.setTipsHintAdapter(hintAdapter);
    }

    public void setTipsHintAdapter2(ArrayAdapter<String> hintAdapter){
        search2.setTipsHintAdapter(hintAdapter);
    }

    public void setAutoCompleteAdapter1(ArrayAdapter<String> autoCompleteAdapter){
        search1.setAutoCompleteAdapter(autoCompleteAdapter);
    }

    public void setAutoCompleteAdapter2(ArrayAdapter<String> autoCompleteAdapter){
        search2.setAutoCompleteAdapter(autoCompleteAdapter);
    }

    public boolean hasFocus1(){
        return search1.hasFocus();
    }

    public boolean hasFocus2(){
        return search2.hasFocus();
    }

    /**
     * search view回调方法
     */
    public interface SearchViewDoubleListener {
    }
}
