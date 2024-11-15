package org.ninetripods.mq.study.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

class TriggerText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    fun forwardText() {
        text = "我要前进"
    }

    fun retreatText() {
        text = "我要后退"
    }
}