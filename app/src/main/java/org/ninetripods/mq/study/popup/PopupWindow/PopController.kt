package org.ninetripods.mq.study.popup.PopupWindow

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.PopupWindow

class PopController(private val context: Context, private val popupWindow: PopupWindow) {
    private var layoutResId = 0 //布局id
    private var mView: View? = null
    var mPopupView: View? = null  //弹窗布局View
    private var mWindow: Window? = null

    fun setView(layoutResId: Int) {
        mView = null
        this.layoutResId = layoutResId
        installContent()
    }

    fun setView(view: View?) {
        mView = view
        layoutResId = 0
        installContent()
    }

    private fun installContent() {
        if (layoutResId != 0) {
            mPopupView = LayoutInflater.from(context).inflate(layoutResId, null)
        } else if (mView != null) {
            mPopupView = mView
        }
        popupWindow.contentView = mPopupView
    }

    /**
     * 设置宽高
     * @param width  宽
     * @param height 高
     */
    private fun setWidthAndHeight(width: Int, height: Int) {
        if (width == 0 || height == 0) {
            //如果没设置宽高，默认是WRAP_CONTENT
            popupWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
            popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            popupWindow.width = width
            popupWindow.height = height
        }
    }

    /**
     * 设置背景灰色程度
     *
     * @param level 0.0f-1.0f
     */
    fun setBackGroundLevel(level: Float) {
        mWindow = (context as Activity).window
        val params = mWindow?.attributes
        params?.alpha = level
        mWindow?.attributes = params
    }

    /**
     * 设置动画
     */
    private fun setAnimStyle(animationStyle: Int) {
        popupWindow.animationStyle = animationStyle
    }

    /**
     * 设置Outside是否可点击
     * @param touchable 是否可点击
     */
    private fun setOutsideTouchable(touchable: Boolean) {
        popupWindow.setBackgroundDrawable(ColorDrawable(0x00000000)) //设置透明背景
        popupWindow.isOutsideTouchable = touchable //设置outside可点击
        popupWindow.isFocusable = touchable
    }

    internal class PopupParams(var mContext: Context) {
        var layoutResId = 0 //布局id
        var mWidth = 0
        var mHeight = 0 //弹窗的宽和高
        var isShowBg = false
        var isShowAnim = false
        var bgLevel = 0f //屏幕背景灰色程度
        var animStyle = 0 //动画Id
        var mView: View? = null
        var isTouchable = true

        fun apply(controller: PopController) {
            when {
                mView != null -> {
                    controller.setView(mView)
                }
                layoutResId != 0 -> {
                    controller.setView(layoutResId)
                }
                else -> {
                    throw IllegalArgumentException("PopupView's contentView is null")
                }
            }
            controller.setWidthAndHeight(mWidth, mHeight)
            controller.setOutsideTouchable(isTouchable) //设置outside可点击
            if (isShowBg) {
                //设置背景
                controller.setBackGroundLevel(bgLevel)
            }
            if (isShowAnim) {
                controller.setAnimStyle(animStyle)
            }
        }
    }
}