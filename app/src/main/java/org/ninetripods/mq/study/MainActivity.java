package org.ninetripods.mq.study;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.ninetripods.mq.study.adapter.MainAdapter;
import org.ninetripods.mq.study.bean.NameBean;
import org.ninetripods.mq.study.bezier.BezierActivity;
import org.ninetripods.mq.study.customView.alipayView.ALiPayActivity;
import org.ninetripods.mq.study.customView.cakeView.ViewActivity;
import org.ninetripods.mq.study.customViewGroup.ViewGroupActivity;
import org.ninetripods.mq.study.interf.MyOnclickListener;
import org.ninetripods.mq.study.multiprocess_client.IpcAidlActivity;
import org.ninetripods.mq.study.path.PathMeasureActivity;
import org.ninetripods.mq.study.path.PathVectorActivity;
import org.ninetripods.mq.study.util.NavitateUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MyOnclickListener {
    private RecyclerView recycle_view;
    private MainAdapter mAdapter;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main2);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, getResources().getString(R.string.app_name), false);
        recycle_view = (RecyclerView) findViewById(R.id.recycle_view);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainAdapter(this);
        mAdapter.setOnItemClickListener(this);
        initBeans();
        recycle_view.setAdapter(mAdapter);
    }

    private void initBeans() {
        List<NameBean> beans = new ArrayList<>();
        String[][] array = {
                {"自定义View", "饼形图", "AliPay", "", ""},
                {"自定义ViewGroup", "五环图", "", "", ""},
                {"属性动画+Path", "PathMeasure", "Path+SVG", "", ""},
                {"贝塞尔曲线", "基本用法示例", "", "", ""},
                {"进程间通信", "Intent", "AIDL", "Messager", "ContentProvider"},
                {"JNI", "", "", "", ""}};
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
                    case R.id.tv_title:
                        break;
                    case R.id.tv_view_one:
                        NavitateUtil.startActivity(this, ViewActivity.class);
                        break;
                    case R.id.tv_view_two:
                        NavitateUtil.startActivity(this, ALiPayActivity.class);
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
                        NavitateUtil.startActivity(this, ViewGroupActivity.class);
                        break;
                }
                break;
            case 2:
                //属性动画+Path
                switch (view.getId()) {
                    case R.id.tv_view_one:
                        NavitateUtil.startActivity(this, PathMeasureActivity.class);
                        break;
                    case R.id.tv_view_two:
                        NavitateUtil.startActivity(this, PathVectorActivity.class);
                        break;
                }
                break;
            case 3:
                //贝塞尔曲线
                switch (view.getId()) {
                    case R.id.tv_view_one:
                        NavitateUtil.startActivity(this, BezierActivity.class);
                        break;
                }
                break;
            case 4:
                switch (view.getId()) {
                    case R.id.tv_view_two:
                        //AIDL
                        NavitateUtil.startActivity(this, IpcAidlActivity.class);
                        break;
                    case R.id.tv_view_three:
                        //Messager
                        NavitateUtil.startActivity(this, IpcAidlActivity.class);
                        break;
                }
                break;

        }
    }
}
