package org.ninetripods.mq.study.nestedScroll.widget

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * android:src：FAB中显示的图标.
   app:backgroundTint：正常的背景颜色
   app:rippleColor：按下时的背景颜色
   app:elevation：正常的阴影大小
   app:pressedTranslationZ：按下时的阴影大小
   app:layout_anchor：设置FAB的锚点，即以哪个控件为参照设置位置
   app:layout_anchorGravity：FAB相对于锚点的位置
   app:fabSize：FAB的大小，normal或mini（分别对应56dp和40dp）
   ScrollFloatingButton: https://blog.csdn.net/gaolh89/article/details/79759404
 */
class ScrollFloatingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FloatingActionButton(context, attrs, defStyleAttr) {


}