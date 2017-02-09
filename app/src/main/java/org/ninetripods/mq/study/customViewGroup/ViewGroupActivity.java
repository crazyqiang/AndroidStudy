package org.ninetripods.mq.study.customViewGroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;

public class ViewGroupActivity extends BaseActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "自定义ViewGroup", true);
    }
}
