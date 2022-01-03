package org.ninetripods.lib_viewpager2.imageLoader

import android.content.Context
import android.view.View

interface ILoader<T : View> {
    fun display(context: Context, content: Any, targetView: T)
    fun createView(context: Context): T
}