package org.ninetripods.mq.study.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by MQ on 2017/1/17.
 */

public class NavitateUtil {
    /**
     * @param context Context
     * @param cls     目标Class
     */
    public static void startActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    /**
     * 跳转到外部浏览器
     *
     * @param context Context
     * @param url     url
     */
    public static void startOutsideBrowser(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * 跳转到Home页
     */
    public static void goHome(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }
}
