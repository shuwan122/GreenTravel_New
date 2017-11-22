package com.example.zero.adapter;

import android.content.Context;

import com.example.zero.bean.RouteSearchBean;
import com.example.zero.bean.ScheduleBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.CommonAdapter;
import com.example.zero.util.ViewHolder;

import java.util.List;

/**
 * Created by ZERO on 2017/11/20.
 */

public class ScheduleDialogAdapter extends CommonAdapter<ScheduleBean> {

    public ScheduleDialogAdapter(Context context, List<ScheduleBean> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, int position) {
        holder.setText(R.id.line,"（" + mData.get(position).getLine() + "）")
                .setText(R.id.stStation,mData.get(position).getStation())
                .setText(R.id.enStation,mData.get(position).getFinal_st())
                .setText(R.id.timeArr,mData.get(position).getArr_time_str());
    }
}
