package org.ninetripods.mq.study.jetpack.mvvm

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import org.ninetripods.mq.study.CommonWebviewActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseMvvmActivity
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseViewModel
import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel
import org.ninetripods.mq.study.jetpack.mvvm.viewmodel.WanViewModel
import org.ninetripods.mq.study.kotlin.ktx.flowWithLifecycle2
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.util.Constant

/**
 * Jetpack MVVM
 */
class MvvmExampleActivity : BaseMvvmActivity() {

    private val mTvContent: TextView by id(R.id.tv_content)
    private val mBtnQuest: Button by id(R.id.btn_request)
    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val mContentView: ViewGroup by id(R.id.cl_content_view)

    private val mViewModel: WanViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.activity_wan_android
    }

    override fun initViews() {
        initToolBar(mToolBar, "Jetpack MVVM", true, true, TYPE_BLOG)
    }

    override fun init() {
        registerEvent()
        mBtnQuest.setOnClickListener {
            //请求数据
            mViewModel.getWanInfoByFlow("")
            //mViewModel.getWanInfoByChannel("")
        }
    }

    override fun getViewModel(): BaseViewModel = mViewModel

    private fun registerEvent() {
        /**
         * Flow方式订阅数据
         * 如果使用封装的flowSingleWithLifecycle2来接收数据，那么不会在前后台切换时重复订阅数据
         */
        mViewModel.mWanFlow.flowWithLifecycle2(this) { list ->
            processModels(list)
        }

        /**
         * Channel方式订阅数据 只能一对一
         */
        mViewModel.channelFlow.flowWithLifecycle2(this) { list ->
            processModels(list)
        }

        /**
         * LiveData
         * 这里使用了扩展函数，等同于mViewModel.mWanLiveData.observe(this) {}
         */
        mViewModel.mWanLiveData.observe(this) { list ->
            processModels(list)
        }
    }

    private fun processModels(list: List<WanModel>?) {
        if (list == null) return
        val builder = StringBuilder()
        for (index in list.indices) {
            //每条数据进行折行显示
            if (index != list.size - 1) {
                builder.append(list[index])
                builder.append("\n\n")
            } else {
                builder.append(list[index])
            }
        }
        mTvContent.text = builder.toString()
    }


    override fun openWebview() {
        CommonWebviewActivity.webviewEntrance(this, Constant.BLOG_JETPACK_MVVM)
    }

    override fun getStatusOwnerView(): View {
        return mContentView
    }

    /**
     * 发生错误时允许重新请求数据
     */
    override fun retryRequest() {
        mViewModel.getWanInfoByFlow("")
    }
}