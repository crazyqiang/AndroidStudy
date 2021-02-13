package org.ninetripods.mq.study.customViewGroup;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;

public class FiveRingsActivity extends BaseActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "自定义ViewGroup", true);
    }
}
