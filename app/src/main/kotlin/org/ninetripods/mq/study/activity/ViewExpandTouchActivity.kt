package org.ninetripods.mq.study.activity

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.kotlin.ktx.showToast

/**
 * View扩大点击区域
 * 1、通过padding扩大
 * 2、通过TouchDelegate
 * 3、自定义View中通过getLocationOnScreen & RectF 去扩大点击区域
 */
class ViewExpandTouchActivity : BaseActivity() {

    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val tvExpandTouch: TextView by id(R.id.tv_view_delegate)

    override fun setContentView() {
        setContentView(R.layout.activity_view_expand_touch)
    }

    override fun initEvents() {
        initToolBar(mToolBar, "扩大View点击区域", true, false)
        tvExpandTouch.run {
            expandTouchView(50.dp2px())
            setOnClickListener { showToast("通过TouchDelegate扩大点击区域") }
        }
    }
}

/**
 * 扩展方法，扩大点击区域
 * NOTE: 需要保证目标targetView有父View，否则无法扩大点击区域
 *
 * @param expandSize 扩大的大小，单位px
 */
fun View.expandTouchView(expandSize: Int = 10.dp2px()) {
    val parentView = (parent as? View)
    parentView?.post {
        val rect = Rect()
        getHitRect(rect) //getHitRect(rect)将视图在父容器中所占据的区域存储到rect中。
        log("rect = $rect")
        rect.left -= expandSize
        rect.top -= expandSize
        rect.right += expandSize
        rect.bottom += expandSize
        log("expandRect = $rect")
        parentView.touchDelegate = TouchDelegate(rect, this)
    }
}