package com.example.zero.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zero.bean.RouteSearchBean;
import com.example.zero.bean.ScheduleBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.CommonAdapter;
import com.example.zero.util.ViewHolder;

import java.util.List;

/**
 * Created by ZERO on 2017/11/20.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<ScheduleBean> scheduleBeanList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView line;
        TextView stStation;
        TextView enStation;
        TextView timeArr;

        public ViewHolder(View view) {
            super(view);
            line = (TextView) view.findViewById(R.id.line);
            stStation = (TextView) view.findViewById(R.id.stStation);
            enStation = (TextView) view.findViewById(R.id.enStation);
            timeArr = (TextView) view.findViewById(R.id.timeArr);
        }
    }

    public ScheduleAdapter(List<ScheduleBean> scheduleBeanList) {
        this.scheduleBeanList = scheduleBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_schedule, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScheduleBean scheduleBean = scheduleBeanList.get(position);
        holder.line.setText("（" + scheduleBean.getLine() + "）");
        holder.stStation.setText(scheduleBean.getStation());
        holder.enStation.setText(scheduleBean.getFinal_st());
        holder.timeArr.setText(scheduleBean.getArr_time_str());
    }

    @Override
    public int getItemCount() {
        return scheduleBeanList.size();
    }
}
