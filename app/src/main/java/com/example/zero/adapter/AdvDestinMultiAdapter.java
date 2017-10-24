package com.example.zero.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.example.zero.bean.AdvDestinMultiBean;
import com.example.zero.greentravel_new.R;

import java.util.ArrayList;

import static com.donkingliang.labels.LabelsView.SelectType.SINGLE;

/**
 * Created by kazu_0122 on 2017/10/11.
 */

public class AdvDestinMultiAdapter extends BaseAdapter {
    private ArrayList<AdvDestinMultiBean> mArrayList;
    private Context mContext;

    public AdvDestinMultiAdapter(ArrayList<AdvDestinMultiBean> list,Context context) {
        super();
        this.mArrayList = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mArrayList == null) {
            return 0;
        } else {
            return this.mArrayList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        if (mArrayList == null) {
            return null;
        } else {
            return this.mArrayList.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adv_checkbox, null, false);
            holder.itemTextView = (TextView) convertView.findViewById(R.id.textViewCheck);
            holder.labelsView = (LabelsView) convertView.findViewById(R.id.labelsViewCheck);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (this.mArrayList != null) {
            final String itemName = this.mArrayList.get(position).getTitle();
            if (holder.itemTextView != null) {
                holder.itemTextView.setText(itemName);
            }
            if(holder.labelsView != null) {
                holder.labelsView.setLabels(mArrayList.get(position).getLabels());
                if (mArrayList.get(position).getIsSingle()) holder.labelsView.setSelectType(SINGLE);
               // Log.e("holder", "S" + mSelected.size() + "+" + position);
//                ArrayList<Integer> intList = mArrayList.get(position).getSelected();
//                //Log.e("holder", "P" + intList.size() + "+" + position);
//                int[] d = new int[intList.size()];
//                for (int j = 0; j < intList.size(); j++) {
//                    d[j] = intList.get(j);
//                   // Log.e("holder", mSubTags.get(position).get(d[j]));
//                }
////                int[] d = new int[2];
////                d[0] =1;
////                d[1] =2;
                holder.labelsView.setSelects(mArrayList.get(position).getSelected());
               // Log.e("holder", "" + d.length + "+" + position);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        TextView itemTextView;
        LabelsView labelsView;
    }

}
