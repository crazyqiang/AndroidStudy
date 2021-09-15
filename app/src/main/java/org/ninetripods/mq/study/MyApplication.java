package org.ninetripods.mq.study;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;

import org.ninetripods.mq.study.jetpack.lifecycle.MyApplicationLifecycleObserver;
import org.ninetripods.mq.study.jetpack.mvvm.base.http.RetrofitUtil;

/**
 * Created by mq on 2018/8/19 下午9:17
 * mqcoder90@gmail.com
 */

public class MyApplication extends Application {

    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //初始化Retrofit
        RetrofitUtil.INSTANCE.initRetrofit();
        //基于Lifecycle，监听Application的生命周期
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new MyApplicationLifecycleObserver());
    }

    public static MyApplication getApplication() {
        return application;
    }
}
