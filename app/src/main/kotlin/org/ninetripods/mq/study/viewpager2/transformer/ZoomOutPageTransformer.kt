package org.ninetripods.mq.study.viewpager2.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2

private const val MIN_SCALE = 0.85f
private const val MIN_ALPHA = 0.5f

class ZoomOutPageTransformer : ViewPager2.PageTransformer {

    /**
     * @param position position参数表示指定页面相对于屏幕中心的位置。
     * 此参数是一个动态属性，会随着用户滚动浏览一系列页面而变化。当页面填满整个屏幕时，其位置值为 0。
     * 当页面刚刚离开屏幕右侧时，其位置值为 1。如果用户在第一页和第二页之间滚动到一半，则第一页的位置为 -0.5，第二页的位置为 0.5
     */
    override fun transformPage(view: View, position: Float) {
        //log("id:" + view.id + ",position:" + position)
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 1 -> { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
                    translationX = if (position < 0) {
                        horzMargin - vertMargin / 2
                    } else {
                        horzMargin + vertMargin / 2
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    // Fade the page relative to its size.
//                    alpha = (MIN_ALPHA +
//                            (((scaleFactor - MIN_SCALE) / (1 - MIN_SCALE)) * (1 - MIN_ALPHA)))
                    alpha = 1.0f
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }
    }
}