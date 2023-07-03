package org.ninetripods.mq.study.span

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

/**
 * Created by mq on 2023/6/27
 */
class ClickableBgColorSpan(private val bgColor: Int, private val block: (View) -> Unit) :
    ClickableSpan() {

    private var isPressed = false

    override fun onClick(widget: View) {
        block.invoke(widget)
    }

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.color = ds.linkColor //继承链接颜色，保持与其他链接样式一致
        ds.isUnderlineText = false //去掉下划线
        ds.bgColor = if (isPressed) bgColor else Color.TRANSPARENT // 设置背景色
    }

    fun setPressed(pressed: Boolean) {
        this.isPressed = pressed
    }

}