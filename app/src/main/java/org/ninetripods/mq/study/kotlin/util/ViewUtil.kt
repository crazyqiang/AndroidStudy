package org.ninetripods.mq.study.kotlin.util

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

object ViewUtil {

    /**
     * 解除childView对应的父子View关系
     */
    fun unBindParentView(childView: View) {
        val parent: ViewParent = childView.parent
        if (parent is ViewGroup) {
            parent.removeView(childView)
        }
    }
}