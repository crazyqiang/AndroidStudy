package org.ninetripods.mq.study.popup;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.popup.PopupWindow.CommonPopupWindow;
import org.ninetripods.mq.study.util.MyLog;
import org.ninetripods.mq.study.util.adapter.PopupAdapter;
import org.ninetripods.mq.study.util.interf.MyOnclickListener;


public class PopupWindowActivity extends BaseActivity implements CommonPopupWindow.ViewInterface {
    private CommonPopupWindow builder;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_popup_window);
    }

    //向下弹出
    public void showDownPop(View view) {
        builder = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_down)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setChildViewOnclickListener(this)
                .setAnimationStyle(R.style.AnimDown)
                .build();
        builder.showAsDropDown(view);
    }

    //向右弹出
    public void showRightPop(View view) {
        CommonPopupWindow builder = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_left_or_right)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimHorizontal)
                .build();
        builder.showAsDropDown(view, view.getWidth(), -view.getHeight());
//        builder.showAtLocation(view.getRootView(), Gravity.START, view.getWidth(), 0);
    }

    //向左弹出
    public void showLeftPop(View view) {
        CommonPopupWindow builder = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_left_or_right)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimRight)
                .build();
        builder.showAsDropDown(view, -(view.getWidth() * 2), -view.getHeight());
//        builder.showAtLocation(findViewById(android.R.id.content), Gravity.END, -view.getWidth(), 0);
    }

    //向上弹出
    public void showUpPop(View view) {
        //TODO 算高度
//        View view1= LayoutInflater.from(this).inflate(R.layout.popup_left_or_right,null);
        builder = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_left_or_right)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setChildViewOnclickListener(this)
                .setAnimationStyle(R.style.AnimUp)
                .build();
        MyLog.e("TTT", "view.getHeight() is " + view.getHeight() + ",builder.getHeight() is " + builder.getHeight());
        builder.showAsDropDown(view, 0, -(view.getHeight() + builder.getHeight()));
    }

    //全屏弹出
    public void showAll(View view) {
        builder = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_up)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
                .setChildViewOnclickListener(this)
                .setAnimationStyle(R.style.AnimUp)
                .build();
        builder.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        //获得PopupWindow布局里的View
        switch (layoutResId) {
            case R.layout.popup_down:
                RecyclerView recycle_view = (RecyclerView) view.findViewById(R.id.recycle_view);
                recycle_view.setLayoutManager(new GridLayoutManager(this, 3));
                PopupAdapter mAdapter = new PopupAdapter(this);
                recycle_view.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new MyOnclickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        toast("position is " + position);
                        if (builder != null) {
                            builder.dismiss();
                        }
                    }
                });
            case R.layout.popup_up:
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (builder != null) {
                            builder.dismiss();
                        }
                        return true;
                    }
                });
                break;
        }
    }
}
