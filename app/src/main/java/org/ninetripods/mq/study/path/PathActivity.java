package org.ninetripods.mq.study.path;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;

public class PathActivity extends BaseActivity {
    private Button btn_path_measure, btn_vector;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_path);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "Path", true);
        btn_path_measure = (Button) findViewById(R.id.btn_path_measure);
        btn_vector = (Button) findViewById(R.id.btn_vector);
    }

    @Override
    public void initEvents() {
        btn_path_measure.setOnClickListener(this);
        btn_vector.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_path_measure:
                startActivity(new Intent(this, PathMeasureActivity.class));
                break;
            case R.id.btn_vector:
                startActivity(new Intent(this, PathVectorActivity.class));
                break;
            default:
                break;
        }
    }
}
