package org.ninetripods.mq.study.span

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

/**
 * Created by mq on 2023/6/25
 */
class CustomClickSpan(private val click: (View) -> Unit) : ClickableSpan() {

    override fun onClick(widget: View) {
        click.invoke(widget)
    }


    /**
     * 通过修改TextPaint来修改文本状态，如修改文本颜色、下划线是否展示等
     * @param paint
     */
    override fun updateDrawState(paint: TextPaint) {
        paint.color = Color.BLUE
        paint.isUnderlineText = true
    }
}