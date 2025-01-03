package org.ninetripods.mq.study.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * @param context 上下文，用于转换 dp 到 px
 * @param heightDp 分割线的高度（单位：dp）
 * @param color 分割线的颜色
 */
fun RecyclerView.createDivider(context: Context, heightDp: Float = 0.5f, color: Int = Color.TRANSPARENT) {
    //创建动态分割线 Drawable
    val shapeDrawable = ShapeDrawable(RectShape()).apply {
        // 设置分割线的高度
        intrinsicHeight = (heightDp * context.resources.displayMetrics.density + 0.5f).toInt() // 将 dp 转换为 px
        // 设置分割线的颜色
        paint.color = color
    }
    //将动态分割线添加到 RecyclerView
    val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
        setDrawable(shapeDrawable)
    }
    this.addItemDecoration(dividerItemDecoration)
}

