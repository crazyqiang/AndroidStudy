package org.ninetripods.mq.study.util.fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.multiprocess_client.AidlActivity;
import org.ninetripods.mq.study.multiprocess_client.BinderActivity;
import org.ninetripods.mq.study.multiprocess_client.IntentActivity;
import org.ninetripods.mq.study.multiprocess_client.MessengerActivity;
import org.ninetripods.mq.study.util.Constant;
import org.ninetripods.mq.study.util.DisplayUtil;
import org.ninetripods.mq.study.util.NavitateUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProcessFragment extends Fragment implements View.OnClickListener {


    public ProcessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_process, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv_bundle = (TextView) view.findViewById(R.id.tv_bundle);
        TextView tv_aidl = (TextView) view.findViewById(R.id.tv_aidl);
        TextView tv_messager = (TextView) view.findViewById(R.id.tv_messager);
        TextView tv_binder = (TextView) view.findViewById(R.id.tv_binder);
        TextView tv_intro = (TextView) view.findViewById(R.id.tv_intro);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(getString(R.string.load_apk));
        tv_intro.setText(DisplayUtil.changeTextColor(getActivity(), stringBuilder, R.color.red_f, 17));
        tv_bundle.setOnClickListener(this);
        tv_aidl.setOnClickListener(this);
        tv_messager.setOnClickListener(this);
        tv_binder.setOnClickListener(this);
        tv_intro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_bundle:
                NavitateUtil.startActivity(getActivity(), IntentActivity.class);
                break;
            case R.id.tv_aidl:
                NavitateUtil.startActivity(getActivity(), AidlActivity.class);
                break;
            case R.id.tv_messager:
                NavitateUtil.startActivity(getActivity(), MessengerActivity.class);
                break;
            case R.id.tv_binder:
                NavitateUtil.startActivity(getActivity(), BinderActivity.class);
                break;
            case R.id.tv_intro:
                NavitateUtil.startOutsideBrowser(getActivity(), Constant.SERVER_APK_URL);
                break;
        }
    }
}
