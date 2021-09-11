package org.ninetripods.mq.study.jetpack.mvvm.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.jetpack.mvvm.base.widget.IStatusView
import org.ninetripods.mq.study.jetpack.mvvm.base.widget.LoadingDialog
import org.ninetripods.mq.study.kotlin.ktx.showToast
import java.lang.reflect.ParameterizedType

abstract class BaseMvvmActivity<VM : BaseViewModel> : BaseActivity(), IStatusView {

    protected lateinit var mViewModel: VM
    protected lateinit var mView: View
    private lateinit var mLoadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLoadingDialog = LoadingDialog(this, false)
        mViewModel = getViewModel()!!
        mViewModel.init(if (intent != null) intent.extras else null)
        init()
        registerEvent()
    }

    /**
     * 获取ViewModel 子类可以复写，自行初始化
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

    override fun showLoadingView(isShow: Boolean) {
        if (isShow) {
            mLoadingDialog.showDialog(this, false)
        } else {
            mLoadingDialog.dismissDialog()
        }
    }

    //TODO 空视图
    override fun showEmptyView() {
        showToast("空视图")
    }

    //错误视图 并且可以重试
    override fun showErrorView(errMsg: String) {
        showToast(errMsg)
    }

    private fun registerEvent() {
        mViewModel.errorLiveData.observe(this) { errMsg ->
            showErrorView(errMsg)
        }
        mViewModel.loadingLiveData.observe(this, { isShow ->
            showLoadingView(isShow)
        })
    }

    abstract fun init()
}