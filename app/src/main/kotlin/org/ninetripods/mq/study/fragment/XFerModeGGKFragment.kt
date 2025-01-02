package org.ninetripods.mq.study.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.widget.xfermode.EraseView

/**
 * 刮刮卡效果
 */
class XFerModeGGKFragment : BaseFragment() {

    private val mEraseView: EraseView by id(R.id.erase_view)
    private val mBtnRest: Button by id(R.id.btn_reset)
    private val mBtnShow: Button by id(R.id.btn_show_result)

    override fun getLayoutId(): Int {
        return R.layout.fragment_ggk
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBtnRest.setOnClickListener { mEraseView.resetState() }
        mBtnShow.setOnClickListener { mEraseView.showResultDirect() }
    }
}