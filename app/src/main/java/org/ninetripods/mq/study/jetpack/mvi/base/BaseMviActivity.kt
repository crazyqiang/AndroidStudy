package org.ninetripods.mq.study.jetpack.mvi.base

import android.os.Bundle
import android.view.View
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.jetpack.base.widget.StatusViewOwner

/**
 * Mvi基类
 */
abstract class BaseMviActivity : BaseActivity() {

    private lateinit var mStatusViewUtil: StatusViewOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStatusViewUtil = StatusViewOwner(this, getStatusOwnerView()) {
            retryRequest()
        }
        init()
//        registerEvent()
    }

//    abstract fun getVModel(): BaseViewModel

//    private fun registerEvent() {
//        //接收错误信息
//        getVModel().errorFlow.flowWithLifecycle2(this,
//            Lifecycle.State.STARTED) { errMsg ->
//            val errStr = if (!TextUtils.isEmpty(errMsg)) errMsg else "出错了"
//            mStatusViewUtil.showErrorView(errStr)
//        }
//        //接收Loading信息
//        getVModel().loadingFlow.flowWithLifecycle2(this, Lifecycle.State.STARTED) { isShow ->
//            mStatusViewUtil.showLoadingView(isShow)
//        }
//        //接收正常信息
//        getVModel().normalFlow.flowWithLifecycle2(this) {
//            mStatusViewUtil.showMainView()
//        }
//    }

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