package org.ninetripods.mq.study.chatgpt.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GirdItemDecoration(private val rowSpace: Int, private val columnSpace: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            val position = parent.getChildLayoutPosition(view)
            val spanCount = layoutManager.spanCount
            if (position < spanCount) {
                outRect.top = 0
            } else {
                outRect.top = rowSpace
            }

            val last = spanCount - 1
            when (position % spanCount) {
                0 -> {
                    outRect.left = 0
                    outRect.right = columnSpace / 2
                }
                last -> {
                    outRect.left = columnSpace / 2
                    outRect.right = 0
                }
                else -> {
                    outRect.left = 0
                    outRect.right = columnSpace / 2
                }
            }
        }
    }
}