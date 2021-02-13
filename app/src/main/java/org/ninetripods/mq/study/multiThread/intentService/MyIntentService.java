package org.ninetripods.mq.study.multiThread.intentService;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

/**
 * Created by MQ on 2017/7/28.
 */

public class MyIntentService extends IntentService {
    public static final String ACTION_ONE = "action_one";
    public static final String ACTION_TWO = "action_two";
    private int progressOne, progressTwo;

    public MyIntentService() {
        super("intent_service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) return;
        String action = intent.getAction();
        switch (action) {
            case ACTION_ONE:
                while (progressOne < 100) {
                    progressOne++;
                    sendBroadcast(getUpdateIntent(0, progressOne));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case ACTION_TWO:
                while (progressTwo < 100) {
                    progressTwo++;
                    sendBroadcast(getUpdateIntent(1, progressTwo));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
        if (progressOne == 100) {
            sendBroadcast(getUpdateIntent(0, progressOne));
        }
        if (progressTwo == 100) {
            sendBroadcast(getUpdateIntent(1, progressTwo));
        }
    }

    private Intent getUpdateIntent(int type, int progress) {
        Intent intent = new Intent();
        switch (type) {
            case 0:
                intent.setAction(UpdateUIReceiver.UPDATE_ACTION_ONE);
                intent.putExtra(UpdateUIReceiver.UPDATE_ONE_KEY, progress);
                break;
            case 1:
                intent.setAction(UpdateUIReceiver.UPDATE_ACTION_TWO);
                intent.putExtra(UpdateUIReceiver.UPDATE_TWO_KEY, progress);
                break;
        }
        return intent;
    }
}
