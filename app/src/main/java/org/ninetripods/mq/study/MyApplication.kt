package org.ninetripods.mq.study

import android.app.Application
import android.content.Context
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDex
import org.ninetripods.mq.study.jetpack.base.http.RetrofitUtil.initRetrofit
import org.ninetripods.mq.study.jetpack.lifecycle.MyApplicationLifecycleObserver
import org.ninetripods.mq.study.util.SPHook.optimizeSpTask
import org.ninetripods.mq.study.util.WebViewPool

/**
 * Created by mq on 2018/8/19 下午9:17
 * mqcoder90@gmail.com
 */
class MyApplication : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //为方法数超过 64K 的应用启用 MultiDex
        //https://developer.android.com/studio/build/multidex?hl=zh-cn
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        initRetrofit() //初始化Retrofit
        ProcessLifecycleOwner.get().lifecycle.addObserver(MyApplicationLifecycleObserver()) //基于Lifecycle，监听Application的生命周期
        optimizeSpTask() //SP优化
        WebViewPool.preloadWebView() //提前缓存WebView
    }

    companion object {
        var mApplication: MyApplication? = null

        @JvmStatic
        fun getApplication(): MyApplication {
            return mApplication!!
        }
    }
}
