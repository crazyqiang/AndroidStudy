package org.ninetripods.lib_viewpager2.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2


/**
 * ZoomOut缩小页面转换器
 * https://developer.android.com/training/animation/screen-slide-2?hl=zh-cn#zoom-out
 */
private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

class ZoomOutPageTransformer : ViewPager2.PageTransformer {

    /**
     * @param position position参数表示指定页面相对于屏幕中心的位置。
     * 此参数是一个动态属性，会随着用户滚动浏览一系列页面而变化。
     * 1、当页面填满整个屏幕时，其位置值为 0。
     * 2、当页面刚刚离开屏幕右侧时，其位置值为 1。
     * 3、当用户在第一页和第二页之间滚动到一半，则第一页的位置为 -0.5，第二页的位置为 0.5
     */
    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> { // [-Infinity,-1)
                    // 这一页在屏幕左侧
                    alpha = 0f
                }
                position <= 1 -> { // [-1,1]
                    // 修改默认的幻灯片转换以收缩页面
                    val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
                    translationX = if (position < 0) {
                        horzMargin - vertMargin / 2
                    } else {
                        horzMargin + vertMargin / 2
                    }

                    // 缩小页面(在MIN_SCALE和1之间)
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    // 页面根据大小变色
                    alpha = (MIN_ALPHA +
                            (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                }
                else -> { // (1,+Infinity]
                    // 这一页在屏幕右侧。
                    alpha = 0f
                }
            }
        }
    }
}
