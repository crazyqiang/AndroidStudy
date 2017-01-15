package org.ninetripods.mq.study;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_view, btn_view_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initViews();
        initEvents();
    }

    private void initViews() {
        btn_view = (Button) findViewById(R.id.btn_view);
        btn_view_group = (Button) findViewById(R.id.btn_view_group);
    }

    private void initEvents() {
        btn_view.setOnClickListener(this);
        btn_view_group.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_view:
                //跳转到自定义View
                startActivity(new Intent(this, ViewActivity.class));
                break;
            case R.id.btn_view_group:
                //跳转到自定义ViewGroup
                startActivity(new Intent(this, ViewGroupActivity.class));
                break;
            default:
                break;
        }
    }
}
