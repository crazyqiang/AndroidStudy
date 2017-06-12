package org.ninetripods.mq.study.recycle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.recycle.swipe_menu.MyDividerDecoration;
import org.ninetripods.mq.study.recycle.swipe_menu.SwipeRecycleView;
import org.ninetripods.mq.study.util.adapter.SwipeAdapter;

public class SwipeMenuActivity extends BaseActivity {

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_swipe_recycleview);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "滑动菜单", true, false);
        SwipeRecycleView swipe_recycleview = (SwipeRecycleView) findViewById(R.id.swipe_recycleview);
        swipe_recycleview.setLayoutManager(new LinearLayoutManager(this));
        swipe_recycleview.addItemDecoration(new MyDividerDecoration());
        SwipeAdapter swipeAdapter = new SwipeAdapter(this);
        swipe_recycleview.setAdapter(swipeAdapter);
    }

}
