package org.ninetripods.mq.study.util;

import android.content.Context;
import android.content.Intent;

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
}
