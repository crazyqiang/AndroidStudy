package org.ninetripods.mq.study.util.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.popup.PopupWindowActivity;
import org.ninetripods.mq.study.popup.WindowManagerActivity;
import org.ninetripods.mq.study.util.NavitateUtil;

public class PopFragment extends Fragment {

    private TextView tv_popup, tv_floating_window;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_popup = (TextView) view.findViewById(R.id.tv_popup);
        tv_floating_window = (TextView) view.findViewById(R.id.tv_floating_window);
        tv_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavitateUtil.startActivity(getActivity(), PopupWindowActivity.class);
            }
        });
        tv_floating_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavitateUtil.startActivity(getActivity(), WindowManagerActivity.class);
            }
        });
    }

}
