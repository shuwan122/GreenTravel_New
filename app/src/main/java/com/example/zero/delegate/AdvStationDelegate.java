package com.example.zero.delegate;

import android.view.View;

import com.donkingliang.labels.LabelsView;
import com.example.zero.bean.AdvDestinSearchBean;
import com.example.zero.greentravel_new.R;
import com.example.zero.util.ItemViewDelegate;
import com.example.zero.util.RecycleViewHolder;

/**
 * Created by kazu_0122 on 2017/10/19.
 */

public class AdvStationDelegate implements ItemViewDelegate<AdvDestinSearchBean> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.adv_destin_station;
    }

    @Override
    public boolean isForViewType(AdvDestinSearchBean item, int position) {
        return item.isStation();
    }

    @Override
    public void convert(final RecycleViewHolder holder, AdvDestinSearchBean bean, int position) {
        holder.setText(R.id.adv_destin_station_title, bean.getTitle() + "车站");
        holder.setText(R.id.adv_destin_station_keyword, bean.getTime());
        holder.setTextList(R.id.adv_destin_station_labels, bean.getLabels());
        holder.setImageResource(R.id.adv_destin_station_img, bean.getImg());
        final LabelsView lv = holder.getView(R.id.adv_destin_station_labels);
        lv.setSelects(0);
        lv.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(View label, String labelText, int position) {
                //label是被点击的标签，labelText是标签的文字，position是标签的位置。
                lv.setSelects(0);
            }
        });
        if (bean.getToggle()) {
            holder.setImageResource(R.id.adv_destin_station_toggle, R.drawable.slide_up);
        } else holder.setImageResource(R.id.adv_destin_station_toggle, R.drawable.slide_down);
    }
}
