package org.ninetripods.mq.study;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import org.ninetripods.mq.study.bezier.BezierActivity;
import org.ninetripods.mq.study.customView.CustomViewActivity;
import org.ninetripods.mq.study.customView.cakeView.ViewActivity;
import org.ninetripods.mq.study.customViewGroup.ViewGroupActivity;
import org.ninetripods.mq.study.path.PathActivity;
import org.ninetripods.mq.study.path.PathMeasureActivity;

public class MainActivity extends BaseActivity {
    private Button btn_view, btn_view_group, btn_bezier, btn_path;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main2);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, getResources().getString(R.string.app_name), false);
        btn_view = (Button) findViewById(R.id.btn_view);
        btn_view_group = (Button) findViewById(R.id.btn_view_group);
        btn_bezier = (Button) findViewById(R.id.btn_bezier);
        btn_path = (Button) findViewById(R.id.btn_path);
    }

    @Override
    public void initEvents() {
        btn_view.setOnClickListener(this);
        btn_view_group.setOnClickListener(this);
        btn_bezier.setOnClickListener(this);
        btn_path.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_view:
                //跳转到自定义View
                startActivity(new Intent(this, CustomViewActivity.class));
                break;
            case R.id.btn_view_group:
                //跳转到自定义ViewGroup
                startActivity(new Intent(this, ViewGroupActivity.class));
                break;
            case R.id.btn_bezier:
                startActivity(new Intent(this, BezierActivity.class));
                break;
            case R.id.btn_path:
                startActivity(new Intent(this, PathActivity.class));
                break;
            default:
                break;
        }
    }
}
