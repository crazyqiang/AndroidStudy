package org.ninetripods.mq.study;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;

import org.ninetripods.mq.study.jetpack.lifecycle.MyApplicationLifecycleObserver;
import org.ninetripods.mq.study.jetpack.base.http.RetrofitUtil;
import org.ninetripods.mq.study.util.SPHook;
import org.ninetripods.mq.study.util.WebViewPool;

/**
 * Created by mq on 2018/8/19 下午9:17
 * mqcoder90@gmail.com
 */

public class MyApplication extends Application {

    private static MyApplication application;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //为方法数超过 64K 的应用启用 MultiDex
        //https://developer.android.com/studio/build/multidex?hl=zh-cn
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //初始化Retrofit
        RetrofitUtil.INSTANCE.initRetrofit();
        //基于Lifecycle，监听Application的生命周期
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new MyApplicationLifecycleObserver());
        //SP优化
        SPHook.INSTANCE.optimizeSpTask();
        //提前缓存WebView
        WebViewPool.INSTANCE.preloadWebView();
    }

    public static MyApplication getApplication() {
        return application;
    }
}
