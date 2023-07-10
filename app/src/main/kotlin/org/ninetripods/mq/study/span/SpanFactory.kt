package org.ninetripods.mq.study.span

import android.text.Spannable

/**
 * 自定义Spannable.Factory
 * Created by mq on 2023/7/10
 */
class SpanFactory : Spannable.Factory() {

    override fun newSpannable(source: CharSequence?): Spannable {
        return source as Spannable
    }
}