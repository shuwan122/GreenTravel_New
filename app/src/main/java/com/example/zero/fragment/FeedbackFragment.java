package com.example.zero.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zero.greentravel_new.R;

/**
 * Created by jojo on 2017/10/16.
 */

public class FeedbackFragment extends Fragment {
    private View helpFrag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        helpFrag = inflater.inflate(R.layout.fragment_feedback, container, false);
        innitView();
        return helpFrag;
    }

    public void innitView() {

    }
}