//package org.ninetripods.mq.study.jetpack.base
//
//import android.os.Bundle
//import android.view.View
//import androidx.annotation.LayoutRes
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.ViewModelProvider
//import java.lang.reflect.ParameterizedType
//
//abstract class KDBaseActivity<VM : BaseViewModel> : AppCompatActivity(), IShowView {
//
//    protected lateinit var mViewModel: VM
//    protected lateinit var mView: View
//    protected lateinit var mLoadingDialog: LoadingDialog
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mLoadingDialog = LoadingDialog(this, false)
//        setContentView(getLayoutId())
//        mViewModel = getViewModel()!!
//        mViewModel.init(if (intent != null) intent.extras else null)
//        initViews()
//        initEvents()
//        loadState()
//    }
//
//    /**
//     * 获取ViewModel 子类可以复写，自行初始化
//     */
//    protected open fun getViewModel(): VM? {
//        //当前对象超类的Type
//        val type = javaClass.genericSuperclass
//        //ParameterizedType表示参数化的类型
//        if (type != null && type is ParameterizedType) {
//            //返回此类型实际类型参数的Type对象数组
//            val actualTypeArguments = type.actualTypeArguments
//            val tClass = actualTypeArguments[0]
//            return ViewModelProvider(this).get(tClass as Class<VM>)
//        }
//        return null
//    }
//
//    override fun showLoadingView(isShow: Boolean) {
//        if (isShow) {
//            //TODO 优化这个Dialog
//            mLoadingDialog.showDialog(this, false)
//        } else {
//            mLoadingDialog.dismissDialog()
//        }
//    }
//
//    //TODO 空视图
//    override fun showEmptyView() {
//        showToast("空视图")
//    }
//
//    //错误视图 并且可以重试
//    override fun showErrorView() {
//        showToast("错误视图")
//    }
//
//    private fun loadState() {
//        mViewModel.emptyOrErrorLiveData.observe(this, { state ->
//            if (state == Constants.StateEmpty) {
//                showEmptyView()
//            } else {
//                showErrorView()
//            }
//        })
//
//        mViewModel.loadingLiveData.observe(this, { isShow ->
//            showLoadingView(isShow)
//        })
//    }
//
//    @LayoutRes
//    abstract fun getLayoutId(): Int
//
//    abstract fun initViews()
//
//    protected open fun initEvents() {}
//
//}