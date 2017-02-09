package org.ninetripods.mq.study.customView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.customView.alipayView.ALiPayActivity;
import org.ninetripods.mq.study.customView.cakeView.ViewActivity;

public class CustomViewActivity extends BaseActivity {

    private Button btn_view1, btn_view2;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_custom_view);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "自定义View", true);
        btn_view1 = (Button) findViewById(R.id.btn_view1);
        btn_view2 = (Button) findViewById(R.id.btn_view2);
    }

    @Override
    public void initEvents() {
        btn_view1.setOnClickListener(this);
        btn_view2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_view1:
                startActivity(new Intent(this, ViewActivity.class));
                break;
            case R.id.btn_view2:
                startActivity(new Intent(this, ALiPayActivity.class));
                break;
        }
    }
}
