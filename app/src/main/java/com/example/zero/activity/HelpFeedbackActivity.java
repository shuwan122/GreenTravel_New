package com.example.zero.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.zero.fragment.HelpFeedbackFragmentController;
import com.example.zero.greentravel_new.R;

/**
 * Created by jojo on 2017/10/10.
 */

public class HelpFeedbackActivity extends AppCompatActivity {
    private TextView backArrow;
    private RadioGroup radioGroup;
    private HelpFeedbackFragmentController controller;
    private View help, feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_feedback);
        innitView();
        controller = HelpFeedbackFragmentController.getInstance(this, R.id.help_feedback_content);
        controller.showFragment(0);
        /**
         *  监听器
         */
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.help:
                        controller.showFragment(0);
                        help.setBackgroundColor(getResources().getColor(R.color.GreenTheme10, null));
                        feedback.setBackgroundColor(getResources().getColor(R.color.white, null));
                        break;
                    case R.id.feedback:
                        controller.showFragment(1);
                        feedback.setBackgroundColor(getResources().getColor(R.color.GreenTheme10, null));
                        help.setBackgroundColor(getResources().getColor(R.color.white, null));
                    default:
                        break;
                }
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void innitView() {
        backArrow = (TextView) findViewById(R.id.hf_back_arrow);
        radioGroup = (RadioGroup) findViewById(R.id.help_feedback_rg);
        help = (View) findViewById(R.id.help_line);
        feedback = (View) findViewById(R.id.feedback_line);
    }
}
