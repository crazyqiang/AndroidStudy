package org.ninetripods.mq.study.NestedScroll.nested;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.NestedScroll.util.view.ItemFragment;
import org.ninetripods.mq.study.NestedScroll.util.view.SimpleViewPagerIndicator;
import org.ninetripods.mq.study.R;

public class NestedScrollActivity extends BaseActivity {
    private String[] mTitles = new String[]{"英雄", "简介", "功能"};
    private SimpleViewPagerIndicator mIndicator;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private ItemFragment[] mFragments = new ItemFragment[mTitles.length];

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_nested);
    }

    @Override
    public void initViews() {
        mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.id_nested_layout_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_nested_layout_viewpager);
        mIndicator.setTitles(mTitles);
        for (int i = 0; i < mTitles.length; i++) {
            mFragments[i] = new ItemFragment();
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTitles.length;
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void initEvents() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
