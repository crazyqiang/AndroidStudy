package org.ninetripods.mq.study.jetpack.mvi.base

import android.os.Bundle
import android.view.View
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.jetpack.base.widget.StatusViewOwner

/**
 * Mvi基类
 */
abstract class BaseMviActivity : BaseActivity() {

    protected lateinit var mStatusViewUtil: StatusViewOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStatusViewUtil = StatusViewOwner(this, getStatusOwnerView()) {
            retryRequest()
        }
    }

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