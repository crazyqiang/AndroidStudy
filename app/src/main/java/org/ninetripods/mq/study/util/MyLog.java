package org.ninetripods.mq.study.util;

import android.util.Log;

import org.ninetripods.mq.study.BuildConfig;

/**
 * Created by MQ on 2017/3/28.
 */

public class MyLog {
    public static int v(String tag, String msg) {
        if (BuildConfig.IS_SHOW_LOG) {
            return Log.v(tag, msg);
        } else {
            return -1;
        }
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (BuildConfig.IS_SHOW_LOG) {
            return Log.v(tag, msg, tr);
        } else {
            return -1;
        }
    }

    public static int d(String tag, String msg) {
        if (BuildConfig.IS_SHOW_LOG) {
            return Log.d(tag, msg);
        } else {
            return -1;
        }
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (BuildConfig.IS_SHOW_LOG) {
            return Log.d(tag, msg, tr);
        } else {
            return -1;
        }
    }

    public static int i(String tag, String msg) {
        if (BuildConfig.IS_SHOW_LOG) {
            return Log.i(tag, msg);
        } else {
            return -1;
        }
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (BuildConfig.IS_SHOW_LOG) {
            return Log.i(tag, msg, tr);
        } else {
            return -1;
        }
    }

    public static int w(String tag, String msg) {
        if (BuildConfig.IS_SHOW_LOG) {
            return Log.w(tag, msg);
        } else {
            return -1;
        }
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (BuildConfig.IS_SHOW_LOG) {
            return Log.w(tag, msg, tr);
        } else {
            return -1;
        }
    }

    public static int w(String tag, Throwable tr) {
        if (BuildConfig.IS_SHOW_LOG) {
            return Log.w(tag, tr);
        } else {
            return -1;
        }
    }

    public static int e(String tag, String msg) {
        if (BuildConfig.IS_SHOW_LOG) {
            return Log.e(tag, msg);
        } else {
            return -1;
        }
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (BuildConfig.IS_SHOW_LOG) {
            return Log.e(tag, msg, tr);
        } else {
            return -1;
        }
    }
}
