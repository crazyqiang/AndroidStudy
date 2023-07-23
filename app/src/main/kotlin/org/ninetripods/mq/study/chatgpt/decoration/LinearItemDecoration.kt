package org.ninetripods.mq.study.chatgpt.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL

class LinearItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        var layoutManager = parent.layoutManager
        if (layoutManager is LinearLayoutManager) {
            val position = parent.getChildLayoutPosition(view)
            parent.adapter?.apply {
                val lastPosition = itemCount.minus(1)
                if (layoutManager.orientation == VERTICAL) {
                    verticalSpace(outRect, position, lastPosition)
                } else {
                    horizontalSpace(outRect, position, lastPosition)
                }
            }
        }
    }

    private fun verticalSpace(outRect: Rect, position: Int, lastPosition: Int) {
        when (position) {
            0 -> {
                outRect.top = 0
            }
            else -> {
                outRect.top = space
            }
        }
    }

    private fun horizontalSpace(outRect: Rect, position: Int, lastPosition: Int) {
        when (position) {
            0 -> {
                outRect.left = 0
            }
            else -> {
                outRect.left = space
            }
        }
    }
}