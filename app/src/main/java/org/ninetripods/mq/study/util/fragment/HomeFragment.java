package org.ninetripods.mq.study.util.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ninetripods.mq.study.CommonWebviewActivity;
import org.ninetripods.mq.study.NestedScroll.tradition.ScrollListViewActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.bezier.BezierDemoActivity;
import org.ninetripods.mq.study.bezier.QQTrackPointActivity;
import org.ninetripods.mq.study.customView.alipayView.ALiPayActivity;
import org.ninetripods.mq.study.customView.cakeView.ViewActivity;
import org.ninetripods.mq.study.customViewGroup.FiveRingsActivity;
import org.ninetripods.mq.study.customViewGroup.FlowLayoutActivity;
import org.ninetripods.mq.study.multiprocess_client.AidlActivity;
import org.ninetripods.mq.study.multiprocess_client.BinderActivity;
import org.ninetripods.mq.study.multiprocess_client.IntentActivity;
import org.ninetripods.mq.study.multiprocess_client.MessengerActivity;
import org.ninetripods.mq.study.NestedScroll.NestedScrollingActivity;
import org.ninetripods.mq.study.path.PathMeasureActivity;
import org.ninetripods.mq.study.path.PathVectorActivity;
import org.ninetripods.mq.study.popup.PopupWindowActivity;
import org.ninetripods.mq.study.propertyAnimator.ViewPropertyAnimatorActivity;
import org.ninetripods.mq.study.util.Constant;
import org.ninetripods.mq.study.util.NavitateUtil;
import org.ninetripods.mq.study.util.adapter.MainAdapter;
import org.ninetripods.mq.study.util.bean.NameBean;
import org.ninetripods.mq.study.util.interf.MyOnclickListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements MyOnclickListener {
    private RecyclerView recycle_view;
    private MainAdapter mAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);
        recycle_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MainAdapter(getActivity());
        mAdapter.setOnItemClickListener(this);
        initBeans();
        recycle_view.setAdapter(mAdapter);
//        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initBeans() {
        List<NameBean> beans = new ArrayList<>();
        String[][] array = {
                {"自定义View", "饼形图", "AliPay", "", ""},
                {"自定义ViewGroup", "五环图", "流式布局", "", ""},
                {"属性动画+Path", "PathMeasure", "Path+SVG", "ViewPropertyAnimator", ""},
                {"贝塞尔曲线", "基本用法示例", "仿QQ小红点", "", ""},
                {"进程间通信", "Intent", "AIDL", "Messenger", "Binder"},
                {"事件滑动", "NestedScroll", "ScrollView+ListView", "", "", ""},
                {"弹窗", "PopupWindow", "", "", "", ""}
        };
        for (String[] anArray : array) {
            beans.add(new NameBean(anArray[0], anArray[1], anArray[2], anArray[3], anArray[4]));
        }
        mAdapter.addAll(beans);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                //自定义View
                switch (view.getId()) {
                    case R.id.tv_view_one:
                        NavitateUtil.startActivity(getActivity(), ViewActivity.class);
                        break;
                    case R.id.tv_view_two:
                        NavitateUtil.startActivity(getActivity(), ALiPayActivity.class);
                        break;
                    case R.id.tv_view_three:
                        break;
                    case R.id.tv_view_four:
                        break;
                }
                break;
            case 1:
                //自定义ViewGroup
                switch (view.getId()) {
                    case R.id.tv_view_one:
                        //五环图
                        NavitateUtil.startActivity(getActivity(), FiveRingsActivity.class);
                        break;
                    case R.id.tv_view_two:
                        //流式布局
                        NavitateUtil.startActivity(getActivity(), FlowLayoutActivity.class);
                        break;
                }
                break;
            case 2:
                //属性动画+Path
                switch (view.getId()) {
                    case R.id.tv_view_one:
                        NavitateUtil.startActivity(getActivity(), PathMeasureActivity.class);
                        break;
                    case R.id.tv_view_two:
                        NavitateUtil.startActivity(getActivity(), PathVectorActivity.class);
                        break;
                    case R.id.tv_view_three:
                        NavitateUtil.startActivity(getActivity(), ViewPropertyAnimatorActivity.class);
                        break;
                }
                break;
            case 3:
                //贝塞尔曲线
                switch (view.getId()) {
                    case R.id.tv_view_one:
                        //基本用法示例
                        NavitateUtil.startActivity(getActivity(), BezierDemoActivity.class);
                        break;
                    case R.id.tv_view_two:
                        //仿QQ小红点
                        NavitateUtil.startActivity(getActivity(), QQTrackPointActivity.class);
                        break;
                }
                break;
            case 4:
                switch (view.getId()) {
                    case R.id.tv_title:
                        CommonWebviewActivity.webviewEntrance(getActivity(), Constant.PROCESS_URL);
                        break;
                    case R.id.tv_view_one:
                        //Intent
                        NavitateUtil.startActivity(getActivity(), IntentActivity.class);
                        break;
                    case R.id.tv_view_two:
                        //AIDL
                        NavitateUtil.startActivity(getActivity(), AidlActivity.class);
                        break;
                    case R.id.tv_view_three:
                        //Messager
                        NavitateUtil.startActivity(getActivity(), MessengerActivity.class);
                        break;
                    case R.id.tv_view_four:
                        //Binder
                        NavitateUtil.startActivity(getActivity(), BinderActivity.class);
                        break;
                }
                break;
            case 5:
                //嵌套滑动
                switch (view.getId()) {
                    case R.id.tv_view_one:
                        NavitateUtil.startActivity(getActivity(), NestedScrollingActivity.class);
                        break;
                    case R.id.tv_view_two:
                        NavitateUtil.startActivity(getActivity(), ScrollListViewActivity.class);
                        break;
                }
                break;
            case 6:
                //弹窗
                switch (view.getId()) {
                    case R.id.tv_title:
                        break;
                    case R.id.tv_view_one:
                        //PopupWindow
                        NavitateUtil.startActivity(getActivity(), PopupWindowActivity.class);
                        break;
                }
                break;
        }
    }
}
