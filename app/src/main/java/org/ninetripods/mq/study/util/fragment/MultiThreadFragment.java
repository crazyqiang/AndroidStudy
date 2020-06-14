package org.ninetripods.mq.study.util.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.multiThread.AsyncTaskActivity;
import org.ninetripods.mq.study.multiThread.HandlerThreadActivity;
import org.ninetripods.mq.study.multiThread.IntentServiceActivity;
import org.ninetripods.mq.study.multiThread.ThreadPoolActivity;
import org.ninetripods.mq.study.util.NavitateUtil;

public class MultiThreadFragment extends Fragment implements View.OnClickListener {

    private TextView tv_handler_thread, tv_intent_service, tv_asynctask, tv_thread_pool;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multi_thread, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tv_handler_thread = (TextView) view.findViewById(R.id.tv_handler_thread);
        tv_intent_service = (TextView) view.findViewById(R.id.tv_intent_service);
        tv_asynctask = (TextView) view.findViewById(R.id.tv_asynctask);
        tv_thread_pool = (TextView) view.findViewById(R.id.tv_thread_pool);
        //点击事件
        tv_handler_thread.setOnClickListener(this);
        tv_intent_service.setOnClickListener(this);
        tv_asynctask.setOnClickListener(this);
        tv_thread_pool.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_handler_thread:
                NavitateUtil.startActivity(getActivity(), HandlerThreadActivity.class);
                break;
            case R.id.tv_intent_service:
                NavitateUtil.startActivity(getActivity(), IntentServiceActivity.class);
                break;
            case R.id.tv_asynctask:
                NavitateUtil.startActivity(getActivity(), AsyncTaskActivity.class);
                break;
            case R.id.tv_thread_pool:
                NavitateUtil.startActivity(getActivity(), ThreadPoolActivity.class);
                break;
        }
    }
}
