package com.zybang.jump.views.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import org.ninetripods.mq.study.popup.PopupWindow.PopController

class PopWindow private constructor(context: Context) : PopupWindow() {

    val controller: PopController = PopController(context, this)

    /**
     * 在当前View的正上方展示
     * @param target  目标View
     */
    fun showOnTop(target: View, gravity: Int = Gravity.CENTER, hExtra: Float = 0F) {
        showAsDropDown(target,
            -(width - target.measuredWidth) / 2, -(height + target.measuredHeight + hExtra.toInt()))
    }

    override fun getWidth(): Int {
        return controller.mPopupView?.measuredWidth ?: 0
    }

    override fun getHeight(): Int {
        return controller.mPopupView?.measuredHeight ?: 0
    }

    override fun dismiss() {
        super.dismiss()
        controller.setBackGroundLevel(1.0f)
    }

    interface ViewInterface {
        fun getChildView(view: View, layoutResId: Int)
    }

    class Builder(context: Context) {

        private val params: PopController.PopupParams = PopController.PopupParams(context)
        private var listener: ViewInterface? = null

        /**
         * @param layoutResId 设置PopupWindow 布局ID
         */
        fun setView(layoutResId: Int): Builder {
            params.mView = null
            params.layoutResId = layoutResId
            return this
        }

        /**
         * @param view 设置PopupWindow布局
         */
        fun setView(view: View): Builder {
            params.mView = view
            params.layoutResId = 0
            return this
        }

        /**
         * 设置子View
         * @param listener ViewInterface
         */
        fun setChildrenView(listener: ViewInterface?): Builder {
            this.listener = listener
            return this
        }

        /**
         * 设置宽度和高度 如果不设置 默认是wrap_content
         * @param width 宽
         */
        fun setWidthAndHeight(width: Int, height: Int): Builder {
            params.mWidth = width
            params.mHeight = height
            return this
        }

        /**
         * 设置背景灰色程度
         * @param level 0.0f-1.0f
         */
        fun setBackGroundLevel(level: Float): Builder {
            params.isShowBg = true
            params.bgLevel = level
            return this
        }

        /**
         * 是否可点击Outside消失
         * @param touchable 是否可点击
         */
        fun setOutsideTouchable(touchable: Boolean): Builder {
            params.isTouchable = touchable
            return this
        }

        /**
         * 设置动画
         */
        fun setAnimStyle(animStyle: Int): Builder {
            params.isShowAnim = true
            params.animStyle = animStyle
            return this
        }

        fun create(): PopWindow {
            val popupWindow = PopWindow(params.mContext)
            params.apply(popupWindow.controller)
            val popView = popupWindow.controller.mPopupView
            if (listener != null && params.layoutResId != 0 && popView != null) {
                listener?.getChildView(popView, params.layoutResId)
            }
            measureWidthAndHeight(popView)
            return popupWindow
        }

    }

    companion object {
        private fun measureWidthAndHeight(view: View?) {
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            val heightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            view?.measure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}