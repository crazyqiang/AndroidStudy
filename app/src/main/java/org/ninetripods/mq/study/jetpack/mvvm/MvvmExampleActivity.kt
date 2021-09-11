package org.ninetripods.mq.study.jetpack.mvvm

import android.widget.Button
import android.widget.TextView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseMvvmActivity
import org.ninetripods.mq.study.jetpack.mvvm.viewmodel.WanViewModel
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.observe

class MvvmExampleActivity : BaseMvvmActivity<WanViewModel>() {

    private val mTvContent: TextView by id(R.id.tv_content)
    private val mBtnQuest: Button by id(R.id.btn_request)

    override fun getLayoutId(): Int {
        return R.layout.activity_wan_android
    }

    override fun init() {
        mBtnQuest.setOnClickListener {
            //请求数据
            mViewModel.getWanInfo()
        }
        /**
         * 这里使用了扩展函数，等同于mViewModel.drinkLiveData.observe(this) {}
         */
        observe(mViewModel.mWanLiveData) {
            mTvContent.text = it.toString()
        }
    }
}