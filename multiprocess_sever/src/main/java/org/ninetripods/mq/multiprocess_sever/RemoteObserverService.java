package org.ninetripods.mq.multiprocess_sever;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

public class RemoteObserverService extends Service {
    private final int APPLE_INFO = 0x0706;
    final RemoteCallbackList<IRemoteServiceCallBack> mCallbacks = new RemoteCallbackList<>();

    private IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public void registerCallback(IRemoteServiceCallBack cb) throws RemoteException {
            if (cb != null) mCallbacks.register(cb);
        }

        @Override
        public void unregisterCallback(IRemoteServiceCallBack cb) throws RemoteException {
            if (cb != null) mCallbacks.unregister(cb);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TTT", "启动服务");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Apple apple = new Apple("红富士", 10f, getString(R.string.notice_info));
                Message message = Message.obtain();
                message.what = APPLE_INFO;
                message.obj = apple;
                mHandler.sendMessage(message);
            }
        }).start();
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APPLE_INFO:
                    Apple apple = (Apple) msg.obj;
                    //观察者模式，通知所有客户端
                    final int clientNum = mCallbacks.beginBroadcast();
                    Log.e("TTT", "clientNum is " + clientNum);
                    for (int i = 0; i < clientNum; i++) {
                        Log.e("TTT", "000000");
                        IRemoteServiceCallBack callBack = mCallbacks.getBroadcastItem(i);
                        if (callBack != null && apple != null) {
                            try {
                                Log.e("TTT", "111111");
                                callBack.noticeAppleInfo(apple);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        mCallbacks.finishBroadcast();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
