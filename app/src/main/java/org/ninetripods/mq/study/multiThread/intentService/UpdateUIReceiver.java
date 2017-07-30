package org.ninetripods.mq.study.multiThread.intentService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by MQ on 2017/7/28.
 */

public class UpdateUIReceiver extends BroadcastReceiver {

    public static final String UPDATE_ACTION_ONE = "update_action_one";
    public static final String UPDATE_ACTION_TWO = "update_action_two";
    public static final String UPDATE_ONE_KEY = "update_one_key";
    public static final String UPDATE_TWO_KEY = "update_two_key";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        switch (intent.getAction()) {
            case UPDATE_ACTION_ONE:
                int progressOne = intent.getIntExtra(UPDATE_ONE_KEY, 0);
                UpdateUI(0, progressOne);
                break;
            case UPDATE_ACTION_TWO:
                int progressTwo = intent.getIntExtra(UPDATE_TWO_KEY, 0);
                UpdateUI(1, progressTwo);
                break;
        }
    }

    public void UpdateUI(int type, int progress) {

    }

    /**
     * 注册广播接收者
     *
     * @param context Context
     */
    public void register(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPDATE_ACTION_ONE);
        filter.addAction(UPDATE_ACTION_TWO);
        context.registerReceiver(this, filter);
    }

    /**
     * 注销广播接收者
     *
     * @param context Context
     */
    public void unRegister(Context context) {
        try {
            context.unregisterReceiver(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
