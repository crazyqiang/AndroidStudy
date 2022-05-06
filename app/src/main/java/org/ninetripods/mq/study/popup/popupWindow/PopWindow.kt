package org.ninetripods.mq.study.popup.popupWindow

import android.content.Context
import android.view.View
import android.widget.PopupWindow

class PopWindow private constructor(context: Context) : PopupWindow() {

    val controller: PopController = PopController(context, this)

    /**
     * 在当前View的上方展示
     * @param target  目标View
     * @param gravity
     * @param wExtra x轴微调使用 >0时向右移，<0时向左移
     * @param hExtra y轴微调使用 >0时向下移, <0时向上移
     */
    fun showOnTargetTop(
        target: View,
        gravity: Int = CENTER_TOP,
        wExtra: Int = 0,
        hExtra: Int = 0,
    ) {
        var xOff = 0
        when (gravity) {
            CENTER_TOP -> {
                //正上方
                xOff = -(width - target.measuredWidth) / 2
            }
            LEFT_TOP -> {
                //左上方
                xOff = -(width - target.measuredWidth / 2)
            }
            RIGHT_TOP -> {
                //右上方
                xOff = target.measuredWidth / 2
            }
        }
        showAsDropDown(target, xOff + wExtra, -(height + target.measuredHeight) + hExtra)
    }

    /**
     * 在当前View的下方展示
     * @param target  目标View
     * @param gravity
     * @param wExtra x轴微调使用 >0时向右移，<0时向左移
     * @param hExtra y轴微调使用 >0时向下移, <0时向上移
     */
    fun showOnTargetBottom(
        target: View,
        gravity: Int = CENTER_BOTTOM,
        wExtra: Int = 0,
        hExtra: Int = 0,
    ) {
        var xOff = 0
        when (gravity) {
            CENTER_BOTTOM -> {
                //正下方
                xOff = -(width - target.measuredWidth) / 2
            }
            LEFT_BOTTOM -> {
                //左下方
                xOff = -(width - target.measuredWidth / 2)
            }
            RIGHT_BOTTOM -> {
                //右下方
                xOff = target.measuredWidth / 2
            }
        }
        showAsDropDown(target, xOff + wExtra, hExtra)
    }

    /**
     * 在当前View的右侧展示
     * @param target  目标View
     * @param gravity
     * @param wExtra x轴微调使用 >0时向右移，<0时向左移
     * @param hExtra y轴微调使用 >0时向下移, <0时向上移
     */
    fun showOnTargetRight(
        target: View,
        gravity: Int = CENTER_RIGHT,
        wExtra: Int = 0,
        hExtra: Int = 0,
    ) {
        var hOff = 0
        when (gravity) {
            CENTER_RIGHT -> {
                //右侧
                hOff = -(height + target.height) / 2
            }
        }
        showAsDropDown(target, target.measuredWidth + wExtra, hOff + hExtra)
    }

    /**
     * 在当前View的左侧展示
     * @param target  目标View
     * @param gravity
     * @param wExtra x轴微调使用 >0时向右移，<0时向左移
     * @param hExtra y轴微调使用 >0时向下移, <0时向上移
     */
    fun showOnTargetLeft(
        target: View,
        gravity: Int = CENTER_LEFT,
        wExtra: Int = 0,
        hExtra: Int = 0,
    ) {
        var hOff = 0
        when (gravity) {
            CENTER_LEFT -> {
                //左侧
                hOff = -(height + target.height) / 2
            }
        }
        showAsDropDown(target, -width + wExtra, hOff + hExtra)
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
        fun getChildView(view: View, layoutResId: Int, pop: PopupWindow)
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
            if (listener != null && popView != null) {
                listener?.getChildView(popView, params.layoutResId, popupWindow)
            }
            measureWidthAndHeight(popView)
            return popupWindow
        }

    }

    companion object {

        const val CENTER_TOP = 0 //正上方
        const val LEFT_TOP = 1 //左上方
        const val RIGHT_TOP = 2 //右上方
        const val CENTER_BOTTOM = 3 //正下方
        const val LEFT_BOTTOM = 4 //左下方
        const val RIGHT_BOTTOM = 5 //右下方
        const val CENTER_RIGHT = 6 //右侧
        const val CENTER_LEFT = 7 //左侧

        fun measureWidthAndHeight(view: View?) {
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            val heightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            view?.measure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}