package org.ninetripods.mq.study.popup.WindowManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import org.ninetripods.mq.study.MyApplication;
import org.ninetripods.mq.study.popup.WindowManager.view.SmallWindowView;

/**
 * Created by mq on 2018/8/19 下午4:38
 * mqcoder90@gmail.com
 */

public class WindowController implements View.OnTouchListener {

    @SuppressLint("StaticFieldLeak")
    private static WindowController instance;

    private WindowManager windowManager;

    private WindowManager.LayoutParams layoutParams;

    private Context mContext;

    private SmallWindowView sys_view;

    private WindowController() {
        this.mContext = MyApplication.getApplication();
    }

    public static WindowController getInstance() {
        if (instance == null) {
            synchronized (WindowController.class) {
                if (instance == null) {
                    instance = new WindowController();
                }
            }
        }
        return instance;
    }


    /**
     * 显示悬浮窗
     */
    @SuppressLint("ClickableViewAccessibility")
    public void showThumbWindow() {
        if (sys_view != null) return;
        sys_view = new SmallWindowView(mContext);
        sys_view.setText("50%");
        sys_view.setOnTouchListener(this);
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = 0, screenHeight = 0;
        if (windowManager != null) {
            //获取屏幕的宽和高
            Point point = new Point();
            windowManager.getDefaultDisplay().getSize(point);
            screenWidth = point.x;
            screenHeight = point.y;
            layoutParams = new WindowManager.LayoutParams();
//            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.width = 200;
            layoutParams.height = 200;
            //设置type
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //26及以上必须使用TYPE_APPLICATION_OVERLAY   @deprecated TYPE_PHONE
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            //设置flags
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            layoutParams.gravity = Gravity.START | Gravity.TOP;
            //背景设置成透明
            layoutParams.format = PixelFormat.TRANSPARENT;
            layoutParams.x = screenWidth;
            layoutParams.y = screenHeight / 2;
            //将View添加到屏幕上
            windowManager.addView(sys_view, layoutParams);
        }
    }

    /**
     * 更新window
     */
    public void updateWindowLayout() {
        if (windowManager != null && layoutParams != null) {
            windowManager.updateViewLayout(sys_view, layoutParams);
        }
    }


    /**
     * 关闭悬浮窗
     */
    public void destroyThumbWindow() {
        if (windowManager != null && sys_view != null) {
            windowManager.removeView(sys_view);
            sys_view = null;
        }
    }


    private int mLastY, mLastX;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int mInScreenX = (int) event.getRawX();
        int mInScreenY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                layoutParams.x += mInScreenX - mLastX;
                layoutParams.y += mInScreenY - mLastY;
                mLastX = mInScreenX;
                mLastY = mInScreenY;
                updateWindowLayout();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}
