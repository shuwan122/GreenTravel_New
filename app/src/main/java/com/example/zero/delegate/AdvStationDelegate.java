package com.example.zero.delegate;

import android.content.Context;
import android.view.View;

import com.donkingliang.labels.LabelsView;
import com.example.zero.bean.AdvDestinSearchBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.ItemViewDelegate;
import com.example.zero.util.RecycleViewHolder;

/**
 * Created by kazu_0122 on 2017/10/19.
 */

public class AdvStationDelegate implements ItemViewDelegate<AdvDestinSearchBean>
{

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.adv_destin_station;
    }

    @Override
    public boolean isForViewType(AdvDestinSearchBean item, int position)
    {
        return item.isStation();
    }

    @Override
    public void convert(Context context,final RecycleViewHolder holder, AdvDestinSearchBean bean, int position)
    {
        holder.setText(R.id.adv_destin_station_title,bean.getTitle()+"站");
        holder.setText(R.id.adv_destin_station_keyword,"附近有"+bean.getComments()+"家相关商家");
        holder.setImageResource(R.id.adv_destin_station_img, R.drawable.station);
        
        if(bean.getToggle()) {
            holder.setImageResource(R.id.adv_destin_station_toggle,R.drawable.slide_up);
        }
        else {
            holder.setImageResource(R.id.adv_destin_station_toggle,R.drawable.slide_down);
        }
    }
}
