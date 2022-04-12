package org.ninetripods.mq.study.util.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ninetripods.mq.study.nestedScroll.CoordinatorLayoutToolbarActivity;
import org.ninetripods.mq.study.nestedScroll.CustomBehaviorActivity;
import org.ninetripods.mq.study.nestedScroll.NestedScrollActivity;
import org.ninetripods.mq.study.nestedScroll.tradition.ScrollListViewActivity;
import org.ninetripods.mq.study.nestedScroll.tradition.ScrollViewPagerActivity;
import org.ninetripods.mq.study.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NestedScrollFragment extends Fragment {

    private TextView tv_scroll_listView, tv_nested_child_parent;
    private TextView tv_scroll_viewpager, tv_coordinate_toolbar;
    private TextView mTvBehavior;


    public NestedScrollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nested_scroll, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tv_scroll_listView = view.findViewById(R.id.tv_scroll_listView);
        tv_scroll_viewpager = view.findViewById(R.id.tv_scroll_viewpager);
        tv_coordinate_toolbar = view.findViewById(R.id.tv_coordinate_toolbar);
        tv_nested_child_parent = view.findViewById(R.id.tv_nested_child_parent);
        mTvBehavior = view.findViewById(R.id.tv_custom_behavior);
        tv_scroll_listView.setOnClickListener(v -> startActivity(new Intent(getActivity(), ScrollListViewActivity.class)));
        tv_scroll_viewpager.setOnClickListener(v -> startActivity(new Intent(getActivity(), ScrollViewPagerActivity.class)));
        tv_coordinate_toolbar.setOnClickListener(v -> startActivity(new Intent(getActivity(), CoordinatorLayoutToolbarActivity.class)));
        tv_nested_child_parent.setOnClickListener(v -> startActivity(new Intent(getActivity(), NestedScrollActivity.class)));
        mTvBehavior.setOnClickListener(v -> startActivity(new Intent(getActivity(), CustomBehaviorActivity.class)));
    }
}
