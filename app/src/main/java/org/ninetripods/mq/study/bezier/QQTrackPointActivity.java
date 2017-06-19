package org.ninetripods.mq.study.bezier;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.recycle.swipe_menu.MyDividerDecoration;
import org.ninetripods.mq.study.recycle.swipe_menu.SwipeRecycleView;
import org.ninetripods.mq.study.util.adapter.SwipeAdapter;
import org.ninetripods.mq.study.util.bean.QQPointBean;

import java.util.ArrayList;
import java.util.List;

public class QQTrackPointActivity extends BaseActivity {

    private List<QQPointBean> list;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_swipe_recycleview);
    }

    @Override
    public void initViews() {
        initDatas();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "仿QQ小红点", true, false);
        SwipeRecycleView swipe_recycleview = (SwipeRecycleView) findViewById(R.id.swipe_recycleview);
        swipe_recycleview.setLayoutManager(new LinearLayoutManager(this));
        swipe_recycleview.addItemDecoration(new MyDividerDecoration());
        SwipeAdapter swipeAdapter = new SwipeAdapter(this, list);
        swipe_recycleview.setAdapter(swipeAdapter);
    }

    private void initDatas() {
        list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            QQPointBean bean = new QQPointBean((i + 1) * 2);
            list.add(bean);
        }
    }

}
