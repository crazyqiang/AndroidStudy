package org.ninetripods.mq.study.NestedScroll.tradition;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.NestedScroll.util.adapter.MyPagerAdapter;
import org.ninetripods.mq.study.NestedScroll.util.view.CustomViewPager;
import org.ninetripods.mq.study.R;

public class ScrollViewPagerActivity extends BaseActivity {

    private CustomViewPager view_pager;
    private MyPagerAdapter myPagerAdapter;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_scroll_view_pager);
    }

    @Override
    public void initViews() {
        view_pager = (CustomViewPager) findViewById(R.id.view_pager);
        myPagerAdapter = new MyPagerAdapter(this);
        view_pager.setAdapter(myPagerAdapter);
    }

    @Override
    public void initEvents() {
        super.initEvents();
    }
}
