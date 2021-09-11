package org.ninetripods.mq.study.jetpack.mvvm

import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseMvvmActivity
import org.ninetripods.mq.study.jetpack.mvvm.viewmodel.DrinkViewModel
import org.ninetripods.mq.study.kotlin.ktx.observe

class DrinkMvvmActivity : BaseMvvmActivity<DrinkViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_drink
    }

    override fun initViews() {

    }

    override fun initEvents() {
        //请求数据
        mViewModel.getDrinkInfo()
        /**
         * 这里使用了扩展函数，等同于mViewModel.drinkLiveData.observe(this) {}
         */
        observe(mViewModel.drinkLiveData) {

        }
    }
}