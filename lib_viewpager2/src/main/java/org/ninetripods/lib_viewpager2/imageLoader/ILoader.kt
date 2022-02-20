package org.ninetripods.lib_viewpager2.imageLoader

import android.view.View
import android.view.ViewGroup

interface ILoader<T : View> {
    fun createView(parent: ViewGroup, viewType: Int): T
    fun display(targetView: T, content: Any, position: Int)
    fun getItemViewType(position: Int): Int
}