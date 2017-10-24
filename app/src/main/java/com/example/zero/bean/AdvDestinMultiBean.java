package com.example.zero.bean;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by kazu_0122 on 2017/10/17.
 */

public class AdvDestinMultiBean {
    private String title;
    private Boolean isSingle;
    private int[] selected;
    private ArrayList<String> labels;

    public AdvDestinMultiBean(String title, Boolean isSingle ,ArrayList<Integer> selects,ArrayList<String> labels) {
        this.title = title;
        this.isSingle = isSingle;
        this.selected = new int[selects.size()];
        for (int j = 0; j < selects.size(); j++) {
            selected[j] = selects.get(j);
        }
        this.labels = labels;
    }

    public void setSelected(ArrayList<Integer> selects) {
        this.selected = new int[selects.size()];
        for (int j = 0; j < selects.size(); j++) {
            selected[j] = selects.get(j);
        }
    }

    public String getTitle() {
        return title;
    }

    public Boolean getIsSingle() {
        return isSingle;
    }

    public int[] getSelected() {
        return selected;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }
}
