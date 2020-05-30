package com.ninetripods.sydialoglib.manager;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

/**
 * Created by mq on 2018/10/26 下午6:05
 * mqcoder90@gmail.com
 */

public class ScreenUtil {


    public static int dp2px(Context context, float dpValue) {
        return (int) ((float) ((int) context.getResources().getDisplayMetrics().density) * dpValue + 0.5F);
    }

    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5F);
    }


    private static Point point = new Point();

    /**
     * 获取屏幕宽度
     *
     * @param activity Activity
     * @return ScreenWidth
     */
    public static int getScreenWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        if (display != null) {
            display.getSize(point);
            return point.x;
        }
        return 0;
    }

    /**
     * 获取屏幕高度
     *
     * @param activity Activity
     * @return ScreenHeight
     */
    public static int getScreenHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        if (display != null) {
            display.getSize(point);
            return point.y - getStatusBarHeight(activity);
        }
        return 0;
    }

    /**
     * 计算statusBar高度
     *
     * @param activity Activity
     * @return statusBar高度
     */
    public static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 计算navigationBar高度
     *
     * @param activity navigationBar高度
     * @return navigationBar高度
     */
    public static int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }


    public static boolean isNavigationBarShow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }
}
