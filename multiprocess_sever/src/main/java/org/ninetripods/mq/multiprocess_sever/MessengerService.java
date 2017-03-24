package org.ninetripods.mq.multiprocess_sever;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class MessengerService extends Service {
    private HandlerThread thread;
    private Handler serverHandler;
    private static final int MESS_FROM_CLIENT = 0x0707;
    private static final int ServerThinking = 0x0708;
    private static final int MESS_FROM_SERVER = 0x0709;
    private static final String RESPONSE_KEY = "response_key";
    private Messenger messenger;

    public MessengerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //创建一个名字为my_thread的线程
        thread = new HandlerThread("my_thread");
        //启动一个线程
        thread.start();
        //在这个线程中创建一个handler
        serverHandler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msgFromClient) {
                super.handleMessage(msgFromClient);
                Message replyToClient = Message.obtain(msgFromClient);
                switch (msgFromClient.what) {
                    //根据Message.what来判断执行服务端的哪段代码
                    case MESS_FROM_CLIENT:
                        replyToClient.what = ServerThinking;
                        try {
                            msgFromClient.replyTo.send(replyToClient);
                            Thread.sleep(3 * 1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        replyToClient.what = MESS_FROM_SERVER;
                        Bundle bundle = new Bundle();
                        bundle.putString(RESPONSE_KEY, "服务器：我知道答案啦，是第八个儿子，因为他是八阿哥（bug）");
                        replyToClient.setData(bundle);
                        try {
                            msgFromClient.replyTo.send(replyToClient);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        messenger = new Messenger(serverHandler);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }


}
