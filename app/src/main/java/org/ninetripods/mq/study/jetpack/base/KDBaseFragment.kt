//package com.example.jetpackstudy.mvvm.base
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.annotation.LayoutRes
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.jetpackstNameViewModeludy.mvvm.http.api.IShowView
//import org.ninetripods.mq.study.jetpack.base.BaseViewModel
//import java.lang.reflect.ParameterizedType
//
//abstract class BaseFragment<VM : BaseViewModel> : Fragment(), IShowView {
//
//    protected lateinit var mViewModel: VM
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return LayoutInflater.from(context).inflate(getLayoutId(), container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initViews(view)
//        mViewModel = getViewModel()!!
//        mViewModel.init(arguments)
//        loadState()
//        initEvents()
//    }
//
//    protected open fun getViewModel(): VM? {
//        val type = javaClass.genericSuperclass
//        if (type != null && type is ParameterizedType) {
//            val actualTypeArguments = type.actualTypeArguments
//            val tClass = actualTypeArguments[1]
//            return ViewModelProvider(
//                this,
//                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
//            )
//                .get(tClass as Class<VM>)
//        }
//        return null
//    }
//
//    private fun loadState() {
//        mViewModel.emptyOrErrorLiveData.observe(viewLifecycleOwner, { state ->
//            if (state == 0) {
//                showEmptyView()
//            } else {
//                showErrorView()
//            }
//        })
//
//        mViewModel.loadingLiveData.observe(viewLifecycleOwner, { isShow ->
//            showLoadingView(isShow)
//        })
//    }
//
//    override fun showLoadingView(isShow: Boolean) {
//    }
//
//    override fun showEmptyView() {
//    }
//
//    override fun showErrorView() {
//    }
//
//    @LayoutRes
//    abstract fun getLayoutId(): Int
//
//    abstract fun initViews(view: View)
//
//    protected open fun initEvents() {}
//
//}