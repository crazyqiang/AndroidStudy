package org.ninetripods.mq.study.multiThread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.multiThread.asynctask.Scheduler;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;

public class AsyncTaskActivity extends BaseActivity {

    private TextView tv_executor_serial, tv_executor_pool;
    private TextView tv1, tv2, tv3, tv4, tv5, tv6;
    private ProgressBar p1, p2, p3, p4, p5, p6;
    private static final int THREAD_UPDATE_1 = 0x100;
    private static final int THREAD_UPDATE_2 = 0x101;
    private static final int THREAD_UPDATE_3 = 0x102;
    private static final int THREAD_UPDATE_4 = 0x103;
    private static final int THREAD_UPDATE_5 = 0x104;
    private static final int THREAD_UPDATE_6 = 0x105;
    private MyHandler myHandler;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_async_task_scheduler);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        initToolBar(toolbar, "AsyncTask", true, false);
        tv1 = findViewById(R.id.progress_bar1).findViewById(R.id.tv_progress);
        tv2 = findViewById(R.id.progress_bar2).findViewById(R.id.tv_progress);
        tv3 = findViewById(R.id.progress_bar3).findViewById(R.id.tv_progress);
        tv4 = findViewById(R.id.progress_bar4).findViewById(R.id.tv_progress);
        tv5 = findViewById(R.id.progress_bar5).findViewById(R.id.tv_progress);
        tv6 = findViewById(R.id.progress_bar6).findViewById(R.id.tv_progress);

        p1 = findViewById(R.id.progress_bar1).findViewById(R.id.progress_bar);
        p2 = findViewById(R.id.progress_bar2).findViewById(R.id.progress_bar);
        p3 = findViewById(R.id.progress_bar3).findViewById(R.id.progress_bar);
        p4 = findViewById(R.id.progress_bar4).findViewById(R.id.progress_bar);
        p5 = findViewById(R.id.progress_bar5).findViewById(R.id.progress_bar);
        p6 = findViewById(R.id.progress_bar6).findViewById(R.id.progress_bar);
        tv_executor_serial = findViewById(R.id.tv_executor_serial);
        tv_executor_pool = findViewById(R.id.tv_executor_pool);
        init();
        myHandler = new MyHandler(this);
    }

    private void init() {
        p1.setProgress(0);
        p1.setMax(100);
        p2.setProgress(0);
        p2.setMax(100);
        p3.setProgress(0);
        p3.setMax(100);
        p4.setProgress(0);
        p4.setMax(100);
        p5.setProgress(0);
        p5.setMax(100);
        p6.setProgress(0);
        p6.setMax(100);
    }

    @Override
    public void initEvents() {
        tv_executor_serial.setOnClickListener(this);
        tv_executor_pool.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_executor_serial:
                startLoad(Scheduler.serialExecutor(), THREAD_UPDATE_1);
                startLoad(Scheduler.serialExecutor(), THREAD_UPDATE_2);
                startLoad(Scheduler.serialExecutor(), THREAD_UPDATE_3);
                startLoad(Scheduler.serialExecutor(), THREAD_UPDATE_4);
                startLoad(Scheduler.serialExecutor(), THREAD_UPDATE_5);
                startLoad(Scheduler.serialExecutor(), THREAD_UPDATE_6);
                break;
            case R.id.tv_executor_pool:
                startLoad(Scheduler.threadPoolExecutor(), THREAD_UPDATE_1);
                startLoad(Scheduler.threadPoolExecutor(), THREAD_UPDATE_2);
                startLoad(Scheduler.threadPoolExecutor(), THREAD_UPDATE_3);
                startLoad(Scheduler.threadPoolExecutor(), THREAD_UPDATE_4);
                startLoad(Scheduler.threadPoolExecutor(), THREAD_UPDATE_5);
                startLoad(Scheduler.threadPoolExecutor(), THREAD_UPDATE_6);
                break;
        }
    }

    private void startLoad(Executor executor, final int what) {
        Scheduler.asyn(executor, new Runnable() {
            @Override
            public void run() {
                int num = 0;
                while (num < 100) {
                    num++;
                    sendMsg(num, what);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //发送消息
    private void sendMsg(int num, int what) {
        Message message = Message.obtain();
        message.what = what;
        message.arg1 = num;
        myHandler.sendMessage(message);
    }

    private final class MyHandler extends Handler {

        private WeakReference<Context> activity;

        MyHandler(Context context) {
            activity = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            if (activity.get() == null) return;
            switch (msg.what) {
                case THREAD_UPDATE_1:
                    tv1.setText(msg.arg1 + "%");
                    p1.setProgress(msg.arg1);
                    break;
                case THREAD_UPDATE_2:
                    tv2.setText(msg.arg1 + "%");
                    p2.setProgress(msg.arg1);
                    break;
                case THREAD_UPDATE_3:
                    tv3.setText(msg.arg1 + "%");
                    p3.setProgress(msg.arg1);
                    break;
                case THREAD_UPDATE_4:
                    tv4.setText(msg.arg1 + "%");
                    p4.setProgress(msg.arg1);
                    break;
                case THREAD_UPDATE_5:
                    tv5.setText(msg.arg1 + "%");
                    p5.setProgress(msg.arg1);
                    break;
                case THREAD_UPDATE_6:
                    tv6.setText(msg.arg1 + "%");
                    p6.setProgress(msg.arg1);
                    break;
            }
        }
    }
}
