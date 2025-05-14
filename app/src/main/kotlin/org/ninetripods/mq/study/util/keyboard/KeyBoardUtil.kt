package org.ninetripods.mq.study.util.keyboard

import android.app.Activity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * 监听键盘状态
 */
class KeyBoardUtil {
    private var preImeStatus = false // 上次键盘是否可见
    private var lastImeHeight = 0    // 上次回调的键盘高度

    fun setKeyboardStatusListener(activity: Activity, listener: (Boolean, Int) -> Unit) {
        ViewCompat.setOnApplyWindowInsetsListener(activity.findViewById(android.R.id.content)) { v, insets ->
            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
            //键盘底部距离屏幕底部的距离，包含了系统NavigationBar的高度（如果它此时是可见的）
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
            val navInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            //键盘高度至少为0
            val imeHeight = (imeInsets - navInsets).coerceAtLeast(0)

            /**
             * case1：键盘状态发生变化
             * case2：键盘已显示，但高度明显增大（从动画过渡为最终高度）
             */
            if (imeVisible != preImeStatus || (imeVisible && imeHeight > lastImeHeight)) {
                listener.invoke(imeVisible, imeHeight)
                //log("键盘监听 =======> imeVisible: $imeVisible,imeInsets:$imeInsets,navInsets:$navInsets,imeHeight: $imeHeight")
            }
            lastImeHeight = imeHeight
            preImeStatus = imeVisible
            //手动将Insets继续分发下去，否则会导致系统忽略状态栏、导航栏等Insets
            ViewCompat.onApplyWindowInsets(v, insets)
        }
    }
}