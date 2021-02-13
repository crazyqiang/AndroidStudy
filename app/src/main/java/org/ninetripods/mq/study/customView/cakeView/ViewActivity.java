package org.ninetripods.mq.study.customView.cakeView;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;


import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends BaseActivity {
    private Toolbar toolbar;
    private List<CakeBean> beans;
    private String[] names = {"php", "object-c", "c", "c++", "java", "android", "linux"};
    private float[] values = {2f, 2f, 3f, 4f, 5f, 6f, 7f};
    private int[] colArrs = {Color.RED, Color.parseColor("#4ebcd3"), Color.MAGENTA, Color.YELLOW, Color.GREEN, Color.parseColor("#f68b2b"), Color.parseColor("#6fb30d")};//圆弧颜色

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTitle("自定义View");
        setContentView(R.layout.activity_view);
        initData();
        initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        beans = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            CakeBean bean = new CakeBean();
            bean.name = names[i];
            bean.value = values[i];
            bean.mColor = colArrs[i];
            beans.add(bean);
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        CakeView cake_view = (CakeView) findViewById(R.id.cake_view);
        cake_view.setData(beans);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "自定义View", true);
    }

}
