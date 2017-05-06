package org.ninetripods.mq.study.materialDesign;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.util.adapter.CardAdapter;

public class ViewPagerActivity extends BaseActivity {
    private ViewPager view_pager;
    private CardAdapter mAdapter;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_view_pager);
    }

    @Override
    public void initViews() {
        view_pager = (ViewPager) findViewById(R.id.vp_viewpager);
        mAdapter = new CardAdapter();
        view_pager.setAdapter(mAdapter);
        view_pager.setPageTransformer(true, new DepthPageTransformer());
        view_pager.setOffscreenPageLimit(3);
    }
}
