package org.ninetripods.mq.study.fragment.nestedScroll

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log

/**
 * BottomSheet Fragment
 */
class BottomSheetFragment : BaseFragment() {

    private lateinit var mBehavior: BottomSheetBehavior<LinearLayout>
    private val mBottomSheet: LinearLayout by id(R.id.bottomSheet)
    private val mBtnClose: Button by id(R.id.closeButton)

    override fun getLayoutId(): Int {
        return R.layout.layout_bottom_sheet
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBehavior = BottomSheetBehavior.from(mBottomSheet)
        mBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        mBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> log("1、展开")
                    BottomSheetBehavior.STATE_COLLAPSED -> log("2、收起")
                    BottomSheetBehavior.STATE_HIDDEN -> log("3、隐藏")
                    BottomSheetBehavior.STATE_DRAGGING -> log("4、拖拽中")
                    BottomSheetBehavior.STATE_SETTLING -> log("5、滑动中")
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> log("6、滑动到一半")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                log("滑动偏移: $slideOffset")
            }
        })

        mBtnClose.setOnClickListener {
            mBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }
}

