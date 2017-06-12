package org.ninetripods.mq.study.util.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.recycle.SwipeMenuActivity;
import org.ninetripods.mq.study.util.NavitateUtil;

public class OtherFragment extends Fragment {

    private TextView tv_suspend;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tv_suspend = (TextView) view.findViewById(R.id.tv_suspend);
        tv_suspend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavitateUtil.startActivity(getActivity(), SwipeMenuActivity.class);
            }
        });
    }
}
