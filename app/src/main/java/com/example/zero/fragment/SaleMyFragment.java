package com.example.zero.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zero.adapter.SaleMyCouponAdapter;
import com.example.zero.bean.SaleBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.RequestManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jojo on 2017/9/13.
 */

public class SaleMyFragment extends Fragment {

    private View sale_my_frag;
    private RecyclerView my_recv;
    private List<SaleBean> dataList = new ArrayList<>();
    private Context context;
    public static final int TYPE_GET = 0;//get请求
    public static final int TYPE_POST_JSON = 1;//post请求参数为json
    public static final int TYPE_POST_FORM = 2;//post请求参数为表单
    private String[] sale_my_name = new String[]{"海底捞枫蓝店", "麦当劳", "肯德基", "麦当劳", "肯德基", "肯德基", "肯德基"};
    private String[] sale_my_price = new String[]{"¥ 20", "¥ 30", "¥ 40", "¥ 50", "¥ 60", "¥ 60", "¥ 60"};
    private String[] sale_my_content = new String[]{"券1", "券2", "券3", "券4", "券5", "券6", "券7"};
    private int[] sale_my_img = new int[]{R.drawable.haidilao, R.drawable.mcdonald, R.drawable.kfc, R.drawable.mcdonald, R.drawable.kfc, R.drawable.kfc, R.drawable.kfc};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sale_my_frag = inflater.inflate(R.layout.fragment_sale_my, container, false);
        innitView();
        showCouponData();
        SaleMyCouponAdapter adapter = new SaleMyCouponAdapter(sale_my_frag.getContext(), dataList);
        my_recv.setAdapter(adapter);
        adapter.setOnItemClickListener(new SaleMyCouponAdapter.onRecycleItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), "点击条目 " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBtnClick(View view, int position) {
                Toast.makeText(getContext(), "点击条目按钮 " + position, Toast.LENGTH_SHORT).show();
            }
        });
        context = sale_my_frag.getContext();
        return sale_my_frag;
    }

    /**
     * 初始化view
     */
    private void innitView() {
        my_recv = (RecyclerView) sale_my_frag.findViewById(R.id.sale_my_recv);
        my_recv.setLayoutManager(new LinearLayoutManager(context));
    }

    /**
     * 优惠券内容加载
     */
    private void showCouponData() {
        for (int i = 0; i < sale_my_name.length; i++) {
            SaleBean saleBean = new SaleBean();
            saleBean.setText(sale_my_name[i], sale_my_price[i], sale_my_content[i], sale_my_img[i]);
            dataList.add(saleBean);
        }
    }

//    private RequestManager requestManager = new RequestManager(context);
//    private String longitude, latitude;
//
//    public void getData() {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("longitude", longitude);   //经度
//        params.put("latitude", latitude);    //纬度
//        requestManager.getInstance(context).requestAsyn("xxx/actionUrl", RequestManager.TYPE_GET, params, new RequestManager.ReqCallBack<String>() {
//
//            @Override
//            public void onReqSuccess(String result) {
//                Toast.makeText(getContext(), "请求成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onReqFailed(String errorMsg) {
//                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}
