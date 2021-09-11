package org.ninetripods.mq.study.jetpack.mvvm.base.http.api

/**
 * Created by mq on 2021/8/11 上午12:39
 * mqcoder90@gmail.com
 */
interface IShowView {
    fun showEmptyView() //空视图
    fun showErrorView() //错误视图
    fun showLoadingView(isShow: Boolean) //展示Loading视图
}