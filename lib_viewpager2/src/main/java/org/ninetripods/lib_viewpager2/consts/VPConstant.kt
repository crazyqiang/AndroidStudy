package org.ninetripods.lib_viewpager2.consts

import android.util.Log
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2

fun log(message: String) {
    Log.e("TTT", message)
}

var mOffScreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
var mOrientation = ViewPager2.ORIENTATION_HORIZONTAL
var mPagerTransformer: CompositePageTransformer? = null
var mItemPaddingLeft: Int = 0 //Item之间的padding间隔
var mItemPaddingRight: Int = 0
var mItemPaddingTop: Int = 0
var mItemPaddingBottom: Int = 0