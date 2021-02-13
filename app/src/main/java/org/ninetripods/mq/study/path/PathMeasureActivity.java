package org.ninetripods.mq.study.path;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.path.view.PathMeasureView;

public class PathMeasureActivity extends BaseActivity {
    private Toolbar toolbar;
    private PathMeasureView pathMeasureView;
    private Button btn_start;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_path_measure);
    }

    @Override
    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "PathMeasure", true);
        pathMeasureView = (PathMeasureView) findViewById(R.id.pathMeasureView);
        btn_start = (Button) findViewById(R.id.btn_start);
    }

    @Override
    public void initEvents() {
        btn_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                pathMeasureView.startMove();
                break;
        }
    }
}
