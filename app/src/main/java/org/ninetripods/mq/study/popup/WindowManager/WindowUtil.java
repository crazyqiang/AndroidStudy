package org.ninetripods.mq.study.popup.WindowManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by mq on 2018/8/19 下午10:09
 * mqcoder90@gmail.com
 */

public class WindowUtil {

    /**
     * @param context context
     * @return true表示已同意悬浮窗权限
     */
    public static boolean canOverDraw(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //兼容Android O
            boolean canDraw = Settings.canDrawOverlays(context);
            return canDraw || canDrawOverlaysO(context);
        }
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(context);
    }

    /**
     * 兼容Android O
     *
     * @param context Context
     * @return true if permission granted
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean canDrawOverlaysO(Context context) {
        AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        if (appOpsMgr == null) return false;
        int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context.getPackageName());
        Log.e("TTT", "android:system_alert_window: mode=" + mode);
        return false;

//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        if (windowManager == null) return false;
//        final View view = new View(context);
//        WindowManager.LayoutParams params = new WindowManager.LayoutParams(0, 0,
//                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
//                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, PixelFormat.TRANSLUCENT);
//        view.setLayoutParams(params);
//        windowManager.addView(view, params);
//        windowManager.removeView(view);
//        Log.e("TTT", "000");
//        return true;
    }


    /**
     * 跳转到设置页面
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void jump2Setting(Activity context, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + context.getPackageName()));
        context.startActivityForResult(intent, requestCode);
    }
}
