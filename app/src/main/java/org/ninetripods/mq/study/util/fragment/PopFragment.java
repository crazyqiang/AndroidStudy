package org.ninetripods.mq.study.util.fragment;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.popup.CommonDialogActivity;
import org.ninetripods.mq.study.popup.PopupWindowActivity;
import org.ninetripods.mq.study.popup.WindowManagerActivity;
import org.ninetripods.mq.study.util.NavitateUtil;

public class PopFragment extends Fragment {

    private TextView tv_popup, tv_floating_window, tv_dialog;

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
        tv_dialog = view.findViewById(R.id.tv_dialog);
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
        tv_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavitateUtil.startActivity(getActivity(), CommonDialogActivity.class);
            }
        });
    }

}
