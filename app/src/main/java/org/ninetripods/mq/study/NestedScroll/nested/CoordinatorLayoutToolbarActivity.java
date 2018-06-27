package org.ninetripods.mq.study.NestedScroll.nested;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.NestedScroll.util.adapter.MyFragmentPagerAdapter;
import org.ninetripods.mq.study.NestedScroll.util.adapter.ZJBaseRecyclerAdapter;
import org.ninetripods.mq.study.NestedScroll.util.adapter.ZJViewHolder;
import org.ninetripods.mq.study.NestedScroll.util.view.OnMeasureListView;
import org.ninetripods.mq.study.R;

import java.util.Arrays;
import java.util.List;

public class CoordinatorLayoutToolbarActivity extends BaseActivity {
    private ViewPager view_pager;
    private MyFragmentPagerAdapter pagerAdapter;
    private OnMeasureListView list_view;
    private RecyclerView recycle_view;
    private String[] data = {"Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_coordinator_layout_toolbar);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "CoordinatorLayout+Toolbar", false);

        recycle_view = (RecyclerView) findViewById(R.id.recycle_view);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));
        final List<String> stringList = Arrays.asList(data);
        recycle_view.setAdapter(new ZJBaseRecyclerAdapter(stringList, android.R.layout.simple_list_item_1, null) {
            @Override
            protected void onBindViewHolder(ZJViewHolder holder, Object model, int position) {
                holder.setText(android.R.id.text1, stringList.get(position));
            }
        });
//        list_view = (OnMeasureListView) findViewById(R.id.list_view);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                this, android.R.layout.simple_list_item_1, data);
//        list_view.setAdapter(adapter);
    }

}
