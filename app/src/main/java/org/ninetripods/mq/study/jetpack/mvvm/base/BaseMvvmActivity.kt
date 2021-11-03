package org.ninetripods.mq.study.jetpack.mvvm.base

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.ViewModelProvider
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.jetpack.mvvm.base.widget.StatusViewOwner
import java.lang.reflect.ParameterizedType

abstract class BaseMvvmActivity<VM : BaseViewModel> : BaseActivity() {

    protected lateinit var mViewModel: VM
    private lateinit var mStatusViewUtil: StatusViewOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mStatusViewUtil = StatusViewOwner(this, getStatusOwnerView()) {
            retryRequest()
        }
        mViewModel = getViewModel()!!
        mViewModel.init(if (intent != null) intent.extras else null)
        init()
        registerEvent()
    }

    /**
     * 获取ViewModel 子类可以复写，自行初始化
     * 注：初始化ViewModel，还可以通过activity-ktx、fragment-ktx扩展库
     * 引入方式：
     * 1、Activity中：implementation "androidx.activity:activity-ktx:1.3.1"
     * 2、Fragment中：implementation "androidx.fragment:fragment-ktx:1.3.6"
     * 版本更新参见：https://developer.android.com/kotlin/ktx/extensions-list
     * 初始化方式：val model: VM by viewModels()
     */
    protected open fun getViewModel(): VM? {
        //当前对象超类的Type
        val type = javaClass.genericSuperclass
        //ParameterizedType表示参数化的类型
        if (type != null && type is ParameterizedType) {
            //返回此类型实际类型参数的Type对象数组
            val actualTypeArguments = type.actualTypeArguments
            val tClass = actualTypeArguments[0]
            return ViewModelProvider(this).get(tClass as Class<VM>)
        }
        return null
    }

    private fun registerEvent() {
        //接收错误信息
        mViewModel.errorLiveData.observe(this) { errMsg ->
            val errStr = if (!TextUtils.isEmpty(errMsg)) errMsg else "出错了"
            mStatusViewUtil.showErrorView(errStr)
        }
        //接收Loading信息
        mViewModel.loadingLiveData.observe(this, { isShow ->
            mStatusViewUtil.showLoadingView(isShow)
        })
        //接收正常信息
        mViewModel.normalLiveData.observe(this, {
            mStatusViewUtil.showMainView()
        })
    }

    protected abstract fun init()

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