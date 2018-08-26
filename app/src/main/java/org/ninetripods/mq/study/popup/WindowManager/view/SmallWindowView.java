package org.ninetripods.mq.study.popup.WindowManager.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import org.ninetripods.mq.study.R;

/**
 * Created by mq on 2018/8/26 下午5:55
 * mqcoder90@gmail.com
 */

public class SmallWindowView extends android.support.v7.widget.AppCompatTextView {

    public SmallWindowView(Context context) {
        this(context, null);
    }

    public SmallWindowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmallWindowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackgroundResource(R.drawable.popup_sys_circle_bg);
        setGravity(Gravity.CENTER);
        setTextColor(Color.WHITE);
    }

    /**
     * 设置宽和高的属性
     *
     * @param height
     * @param width
     */
    public void setHW(int height, int width) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
        setLayoutParams(params);
    }
}
