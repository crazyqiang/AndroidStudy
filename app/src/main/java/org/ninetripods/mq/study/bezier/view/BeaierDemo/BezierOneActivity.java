package org.ninetripods.mq.study.bezier.view.BeaierDemo;

import androidx.appcompat.widget.Toolbar;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;

public class BezierOneActivity extends BaseActivity {
    private Toolbar toolbar;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_bezier_one);
    }

    @Override
    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "一阶贝塞尔曲线", true);
    }
}
