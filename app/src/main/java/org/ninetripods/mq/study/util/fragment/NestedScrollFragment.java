package org.ninetripods.mq.study.util.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ninetripods.mq.study.NestedScroll.nested.CoordinatorLayoutToolbarActivity;
import org.ninetripods.mq.study.NestedScroll.nested.NestedScrollActivity;
import org.ninetripods.mq.study.NestedScroll.tradition.ScrollListViewActivity;
import org.ninetripods.mq.study.NestedScroll.tradition.ScrollViewPagerActivity;
import org.ninetripods.mq.study.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NestedScrollFragment extends Fragment {

    private TextView tv_scroll_listView, tv_nested_child_parent;
    private TextView tv_scroll_viewpager, tv_coordinate_toolbar;


    public NestedScrollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nested_scroll, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tv_scroll_listView = (TextView) view.findViewById(R.id.tv_scroll_listView);
        tv_scroll_viewpager = (TextView) view.findViewById(R.id.tv_scroll_viewpager);
        tv_coordinate_toolbar = (TextView) view.findViewById(R.id.tv_coordinate_toolbar);
        tv_nested_child_parent = (TextView) view.findViewById(R.id.tv_nested_child_parent);
        tv_scroll_listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ScrollListViewActivity.class));
            }
        });
        tv_scroll_viewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ScrollViewPagerActivity.class));
            }
        });
        tv_coordinate_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CoordinatorLayoutToolbarActivity.class));
            }
        });
        tv_nested_child_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NestedScrollActivity.class));
            }
        });
    }
}
