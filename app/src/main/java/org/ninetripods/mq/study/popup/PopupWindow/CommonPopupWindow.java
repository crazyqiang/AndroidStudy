package org.ninetripods.mq.study.popup.PopupWindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.util.MyLog;
import org.ninetripods.mq.study.util.adapter.MainAdapter;


/**
 * Created by MQ on 2017/4/8.
 */

public class CommonPopupWindow extends PopupWindow {
    private Window mWindow;

    public interface ViewInterface {
        void getChildView(View view, int layoutResId);
    }

    public CommonPopupWindow() {
    }

    @Override
    public void dismiss() {
        if (mWindow != null) {
            //恢复背景
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.alpha = 1.0f;
            mWindow.setAttributes(params);
        }
        super.dismiss();
    }

    public static class Builder {
        private int layoutResId;//布局id
        private Context context;
        private CommonPopupWindow popupWindow;
        private View mPopupView;//弹窗布局View
        private int mWidth, mHeight;//弹窗的宽和高
        private ViewInterface listener;
        private boolean isShowBg, isShowAnim = false;
        private float bg_level;//背景灰色程度
        private int animationStyle;//动画Id

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setView(int layoutResId) {
            this.layoutResId = layoutResId;
            return this;
        }

        /**
         * 设置宽度
         *
         * @param width 宽
         * @return Builder
         */
        public Builder setWidthAndHeight(int width, int height) {
            this.mWidth = width;
            this.mHeight = height;
            return this;
        }

        /**
         * 设置背景灰色程度
         *
         * @param level 0.0f-1.0f
         * @return Builder
         */
        public Builder setBackGroundLevel(float level) {
            isShowBg = true;
            this.bg_level = level;
            return this;
        }

        /**
         * 设置子View
         *
         * @param listener ViewInterface
         * @return Builder
         */
        public Builder setChildViewOnclickListener(ViewInterface listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 设置动画
         *
         * @return Builder
         */
        public Builder setAnimationStyle(int animationStyle) {
            isShowAnim = true;
            this.animationStyle = animationStyle;
            return this;
        }


        public CommonPopupWindow build() {
            popupWindow = new CommonPopupWindow();
            mPopupView = LayoutInflater.from(context).inflate(layoutResId, null);
            popupWindow.setContentView(mPopupView);
            popupWindow.setWidth(mWidth);//设置宽
            popupWindow.setHeight(mHeight);//设置高
            popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置透明背景
            popupWindow.setOutsideTouchable(true);//设置outside可点击
            popupWindow.setFocusable(true);
            if (isShowBg) {
                //设置背景
                popupWindow.mWindow = ((Activity) context).getWindow();
                WindowManager.LayoutParams params = popupWindow.mWindow.getAttributes();
                params.alpha = bg_level;
                popupWindow.mWindow.setAttributes(params);
            }
            if (isShowAnim) {
                popupWindow.setAnimationStyle(animationStyle);
            }
            if (listener != null) {
                listener.getChildView(mPopupView,layoutResId);
            }
            return popupWindow;
        }

    }
}
