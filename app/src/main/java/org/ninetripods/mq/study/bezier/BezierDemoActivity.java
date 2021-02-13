package org.ninetripods.mq.study.bezier;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.bezier.view.BeaierDemo.BezierOneActivity;
import org.ninetripods.mq.study.bezier.view.BeaierDemo.BezierThreeActivity;
import org.ninetripods.mq.study.bezier.view.BeaierDemo.BezierTwoActivity;
import org.ninetripods.mq.study.util.NavitateUtil;

public class BezierDemoActivity extends BaseActivity {
    private Toolbar toolbar;
    private TextView bezier2, bezier3, bezier1;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_bezier);
    }

    @Override
    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "贝塞尔曲线", true);
        bezier2 = (TextView) findViewById(R.id.bezier2);
        bezier3 = (TextView) findViewById(R.id.bezier3);
        bezier1 = (TextView) findViewById(R.id.bezier1);
    }

    @Override
    public void initEvents() {
        bezier2.setOnClickListener(this);
        bezier3.setOnClickListener(this);
        bezier1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bezier1:
                NavitateUtil.startActivity(this, BezierOneActivity.class);
                break;
            case R.id.bezier2:
                NavitateUtil.startActivity(this, BezierTwoActivity.class);
                break;
            case R.id.bezier3:
                NavitateUtil.startActivity(this, BezierThreeActivity.class);
                break;

        }
    }
}
