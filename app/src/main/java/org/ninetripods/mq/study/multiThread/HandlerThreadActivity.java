package org.ninetripods.mq.study.multiThread;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.CommonWebviewActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.util.Constant;

import java.util.Random;

import static android.os.Build.VERSION.SDK;
import static android.os.Build.VERSION.SDK_INT;

public class HandlerThreadActivity extends BaseActivity {

    private HandlerThread handlerThread;
    private Handler mainHandler;
    private Handler uiHandler;
    private static final int MSG_UPDATE = 1;
    private static final int UI_UPDATE = 3;
    private TextView tv_random, tv_start, tv_pause;
    private volatile boolean isStop = false;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_handler_thread);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "HandlerThread", true, true, 2);
        tv_random = (TextView) findViewById(R.id.tv_random);
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_pause = (TextView) findViewById(R.id.tv_pause);
        handlerThread = new HandlerThread("handler_thread");
        handlerThread.start();
        //在主线程初始化，传入的是HandlerThread中的Looper
        mainHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //Handler是在HandlerThread所在的子线程线程中处理Message
                switch (msg.what) {
                    case MSG_UPDATE:
                        if (isStop) return;
                        try {
                            //更新UI
                            String result = String.valueOf(new Random().nextInt(9000) + 1000);
                            sendMsg(UI_UPDATE, result, uiHandler);
                            //延迟3秒
                            Thread.sleep(2000);
                            //循环发送消息
                            mainHandler.sendEmptyMessage(MSG_UPDATE);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
        };
        //在主线程初始化，传入的是主线程中的Looper
        uiHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //在主线程中处理Message
                switch (msg.what) {
                    case UI_UPDATE:
                        //更新验证码
                        String result = (String) msg.obj;
                        tv_random.setText(result);
                        break;
                }
            }
        };
    }

    @Override
    public void initEvents() {
        tv_start.setOnClickListener(this);
        tv_pause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                isStop = false;
                //发消息给子线程 在子线程中或得验证码
                sendMsg(MSG_UPDATE, mainHandler);
                toast("开始获取验证码");
                break;
            case R.id.tv_pause:
                isStop = true;
                toast("暂停获取验证码");
                break;
        }
    }

    /**
     * 发送消息
     *
     * @param what    message.what
     * @param handler Handler
     */
    private void sendMsg(int what, Handler handler) {
        Message message = Message.obtain();
        message.what = what;
        handler.sendMessage(message);
    }

    /**
     * 发送消息
     *
     * @param what    message.what
     * @param handler Handler
     * @param result  message.obj
     */
    private void sendMsg(int what, String result, Handler handler) {
        Message message = Message.obtain();
        message.what = what;
        message.obj = result;
        handler.sendMessage(message);
    }

    @Override
    protected void onDestroy() {
        if (SDK_INT >= 18) {
            handlerThread.quitSafely();
        } else {
            handlerThread.quit();
        }
        super.onDestroy();
    }

    @Override
    public void openWebview() {
        CommonWebviewActivity.webviewEntrance(this, Constant.HANDLER_THREAD_BLOG);
    }
}
