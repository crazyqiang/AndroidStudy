package com.ninetripods.sydialoglib;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.ninetripods.sydialoglib.manager.SYDialogsManager;


/**
 * Created by mq on 2018/8/31 上午11:39
 * mqcoder90@gmail.com
 */

public abstract class SYBaseDialog extends DialogFragment {
    private View view = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (getLayoutRes() > 0) {
            //调用方通过xml获取view
            view = inflater.inflate(getLayoutRes(), container, false);
        } else if (getDialogView() != null) {
            //调用方直接传入view
            view = getDialogView();
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            if (Build.VERSION.SDK_INT >= 30 && dialog.getWindow() != null) {
                dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
            }
            //如果isCancelable()是false 则会屏蔽物理返回键
            dialog.setCancelable(isCancelable());
            //如果isCancelableOutside()为false 点击屏幕外Dialog不会消失；反之会消失
            dialog.setCanceledOnTouchOutside(isCancelableOutside());
            //如果isCancelable()设置的是false 会屏蔽物理返回键
            dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    return keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && !isCancelable();
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog == null) return;
        Window window = dialog.getWindow();
        if (window == null) return;
        //设置背景色透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置Dialog动画效果
        if (getAnimRes() > 0) {
            window.setWindowAnimations(getAnimRes());
        }
        WindowManager.LayoutParams params = window.getAttributes();
        //设置Dialog的Width
        if (getDialogWidth() > 0) {
            params.width = getDialogWidth();
        } else {
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        //设置Dialog的Height
        if (getDialogHeight() > 0) {
            params.height = getDialogHeight();
        } else {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        //设置屏幕透明度 0.0f~1.0f(完全透明~完全不透明)
        params.dimAmount = getDimAmount();
        params.gravity = getGravity();
        window.setAttributes(params);
    }

    protected View getBaseView() {
        return view;
    }

    protected abstract int getLayoutRes();

    protected abstract View getDialogView();

    protected boolean isCancelableOutside() {
        return true;
    }

    protected int getDialogWidth() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    protected int getDialogHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public float getDimAmount() {
        return 0.2f;
    }

    protected int getGravity() {
        return Gravity.CENTER;
    }

    protected int getAnimRes() {
        return 0;
    }

    private static Point point = new Point();


    /**
     * 获取屏幕宽度
     *
     * @param activity Activity
     * @return ScreenWidth
     */
    public static int getScreenWidth(FragmentActivity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        if (display != null) {
            display.getSize(point);
            return point.x;
        }
        return 0;
    }

    /**
     * 获取屏幕高度
     *
     * @param activity Activity
     * @return ScreenHeight
     */
    public static int getScreenHeight(FragmentActivity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        if (display != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                //api 大于17才有
                display.getRealSize(point);
            } else {
                display.getSize(point);
            }

            //需要减去statusBar的高度  不用考虑navigationBar Display已经自动减去了
            return point.y - getStatusBarHeight(activity);
        }
        return 0;
    }

    public static int getStatusBarHeight(FragmentActivity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    public static int getNavigationBarHeight(FragmentActivity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SYDialogsManager.getInstance().over();
    }
}
