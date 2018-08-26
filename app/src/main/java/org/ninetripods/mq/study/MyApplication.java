package org.ninetripods.mq.study;

import android.app.Application;

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
    }

    public static MyApplication getApplication() {
        return application;
    }
}
