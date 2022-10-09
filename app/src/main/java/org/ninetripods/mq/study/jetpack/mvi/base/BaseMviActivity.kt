package org.ninetripods.mq.study.jetpack.mvi.base

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.jetpack.mvi.MViewModel
import org.ninetripods.mq.study.jetpack.mvvm.base.widget.StatusViewOwner
import org.ninetripods.mq.study.kotlin.ktx.flowWithLifecycle2

/**
 * Mvi基类
 */
abstract class BaseMviActivity : BaseActivity() {

    //TODO 优化这里ViewModel的初始化时机
    protected val mViewModel: MViewModel by viewModels()
    private lateinit var mStatusViewUtil: StatusViewOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStatusViewUtil = StatusViewOwner(this, getStatusOwnerView()) {
            retryRequest()
        }
        init()
        registerEvent()
    }

    private fun registerEvent() {
        //接收错误信息
        mViewModel.errorFlow.flowWithLifecycle2(this, Lifecycle.State.STARTED) { errMsg ->
            val errStr = if (!TextUtils.isEmpty(errMsg)) errMsg else "出错了"
            mStatusViewUtil.showErrorView(errStr)
        }
        //接收Loading信息
        mViewModel.loadingFlow.flowWithLifecycle2(this, Lifecycle.State.STARTED) { isShow ->
            mStatusViewUtil.showLoadingView(isShow)
        }
        //接收正常信息
        mViewModel.normalFlow.flowWithLifecycle2(this) {
            mStatusViewUtil.showMainView()
        }
    }

    protected open fun init() {}

    /**
     * 重新请求
     */
    open fun retryRequest() {}

    /**
     * 展示Loading、Empty、Error视图等
     */
    open fun getStatusOwnerView(): View? {
        return null
    }
}