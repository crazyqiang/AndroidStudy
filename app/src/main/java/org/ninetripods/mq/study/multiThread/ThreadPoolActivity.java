package org.ninetripods.mq.study.multiThread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.CommonWebviewActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.util.Constant;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolActivity extends BaseActivity {


    // 1、ScheduledThreadPool 核心线程是固定的，非核心线程是不固定的，非核心线程闲置时会被立即回收，主要用于
    //      执行定时任务和具有周期性的重复任务
    // 2、SingleThreadExecutor 内部只有一个核心线程，所有任务按顺序执行 统一所有任务到一个线程中，
    //      使得这些任务不用处理线程同步问题
    // 3、CachedThreadPool 只有Integer.MAX_VALUE个非核心线程，任务队列为空集合 适合执行大量的耗时较少的操作
    // 4、FixedThreadPool  只有核心线程并且不会被回收，任务队列没有大小限制

    private ExecutorService threadPool;
    private ScheduledExecutorService scheduledPool;
    private MyHandler myHandler;
    private TextView tv1, tv2, tv3, tv4, tv5;
    private TextView tv_name1, tv_name2, tv_name3, tv_name4, tv_name5;
    private ProgressBar p1, p2, p3, p4, p5;
    private TextView tv_thread1, tv_thread2, tv_thread3, tv_thread4, tv_thread5, tv_thread_all;
    private TextView tv_stop1, tv_stop2, tv_stop3, tv_stop4, tv_stop5, tv_stop_all;
    private TextView tv_pool_type;
    private TextView tv_shutdown, tv_shutdownNow;

    private Future<Integer> future1, future2, future3, future4, future5;
    private static final int THREAD_UPDATE_1 = 0x100;
    private static final int THREAD_UPDATE_2 = 0x101;
    private static final int THREAD_UPDATE_3 = 0x102;
    private static final int THREAD_UPDATE_4 = 0x103;
    private static final int THREAD_UPDATE_5 = 0x104;
    //当前线程池类型 0-ScheduledThreadPool  1-SingleThreadExecutor  2-CachedThreadPool  3-FixedThreadPool
    private int poolType;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_thread_pool);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "线程池", true, false);
        tv1 = (TextView) findViewById(R.id.progress_bar1).findViewById(R.id.tv_progress);
        tv2 = (TextView) findViewById(R.id.progress_bar2).findViewById(R.id.tv_progress);
        tv3 = (TextView) findViewById(R.id.progress_bar3).findViewById(R.id.tv_progress);
        tv4 = (TextView) findViewById(R.id.progress_bar4).findViewById(R.id.tv_progress);
        tv5 = (TextView) findViewById(R.id.progress_bar5).findViewById(R.id.tv_progress);

        tv_name1 = (TextView) findViewById(R.id.progress_bar1).findViewById(R.id.tv_name);
        tv_name2 = (TextView) findViewById(R.id.progress_bar2).findViewById(R.id.tv_name);
        tv_name3 = (TextView) findViewById(R.id.progress_bar3).findViewById(R.id.tv_name);
        tv_name4 = (TextView) findViewById(R.id.progress_bar4).findViewById(R.id.tv_name);
        tv_name5 = (TextView) findViewById(R.id.progress_bar5).findViewById(R.id.tv_name);

        p1 = (ProgressBar) findViewById(R.id.progress_bar1).findViewById(R.id.progress_bar);
        p2 = (ProgressBar) findViewById(R.id.progress_bar2).findViewById(R.id.progress_bar);
        p3 = (ProgressBar) findViewById(R.id.progress_bar3).findViewById(R.id.progress_bar);
        p4 = (ProgressBar) findViewById(R.id.progress_bar4).findViewById(R.id.progress_bar);
        p5 = (ProgressBar) findViewById(R.id.progress_bar5).findViewById(R.id.progress_bar);
        init();
        tv_thread1 = (TextView) findViewById(R.id.tv_thread1);
        tv_thread2 = (TextView) findViewById(R.id.tv_thread2);
        tv_thread3 = (TextView) findViewById(R.id.tv_thread3);
        tv_thread4 = (TextView) findViewById(R.id.tv_thread4);
        tv_thread5 = (TextView) findViewById(R.id.tv_thread5);
        tv_thread_all = (TextView) findViewById(R.id.tv_thread_all);
        tv_pool_type = (TextView) findViewById(R.id.tv_pool_type);
        tv_stop1 = (TextView) findViewById(R.id.tv_stop1);
        tv_stop2 = (TextView) findViewById(R.id.tv_stop2);
        tv_stop3 = (TextView) findViewById(R.id.tv_stop3);
        tv_stop4 = (TextView) findViewById(R.id.tv_stop4);
        tv_stop5 = (TextView) findViewById(R.id.tv_stop5);
        tv_stop_all = (TextView) findViewById(R.id.tv_stop_all);
        tv_shutdown = (TextView) findViewById(R.id.tv_shutdown);
        tv_shutdownNow = (TextView) findViewById(R.id.tv_shutdown_now);
        scheduledPool = Executors.newScheduledThreadPool(4);
        poolType = 0;
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
        tv_name1.setText("线程一");
        tv_name2.setText("线程二");
        tv_name3.setText("线程三");
        tv_name4.setText("线程四");
        tv_name5.setText("线程五");
    }

    @Override
    public void initEvents() {
        tv_thread1.setOnClickListener(this);
        tv_thread2.setOnClickListener(this);
        tv_thread3.setOnClickListener(this);
        tv_thread4.setOnClickListener(this);
        tv_thread5.setOnClickListener(this);
        tv_thread_all.setOnClickListener(this);
        tv_stop1.setOnClickListener(this);
        tv_stop2.setOnClickListener(this);
        tv_stop3.setOnClickListener(this);
        tv_stop4.setOnClickListener(this);
        tv_stop5.setOnClickListener(this);
        tv_stop_all.setOnClickListener(this);
        tv_shutdown.setOnClickListener(this);
        tv_shutdownNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_thread1:
                startThread(future1, THREAD_UPDATE_1);
                break;
            case R.id.tv_thread2:
                startThread(future2, THREAD_UPDATE_2);
                break;
            case R.id.tv_thread3:
                startThread(future3, THREAD_UPDATE_3);
                break;
            case R.id.tv_thread4:
                startThread(future4, THREAD_UPDATE_4);
                break;
            case R.id.tv_thread5:
                startThread(future5, THREAD_UPDATE_5);
                break;
            case R.id.tv_thread_all:
                startThread(future1, THREAD_UPDATE_1);
                startThread(future2, THREAD_UPDATE_2);
                startThread(future3, THREAD_UPDATE_3);
                startThread(future4, THREAD_UPDATE_4);
                startThread(future5, THREAD_UPDATE_5);
                if (poolType == 0) {
                    //ScheduledThreadPool
                    toast("当前线程池是ScheduledThreadPool,延迟2秒后执行");
                } else if (poolType == 1) {
                    //SingleThreadExecutor
                    toast("当前线程池是SingleThreadExecutor");
                } else if (poolType == 2) {
                    //CachedThreadPool
                    toast("当前线程池是CachedThreadPool");
                } else if (poolType == 3) {
                    //FixedThreadPool
                    toast("当前线程池是FixedThreadPool");
                }
                break;
            case R.id.tv_stop1:
                stopThread(future1);
                break;
            case R.id.tv_stop2:
                stopThread(future2);
                break;
            case R.id.tv_stop3:
                stopThread(future3);
                break;
            case R.id.tv_stop4:
                stopThread(future4);
                break;
            case R.id.tv_stop5:
                stopThread(future5);
                break;
            case R.id.tv_stop_all:
                stopThread(future1);
                stopThread(future2);
                stopThread(future3);
                stopThread(future4);
                stopThread(future5);
                break;
            case R.id.tv_shutdown:
                if (threadPool != null && !threadPool.isShutdown()) {
                    threadPool.shutdown();
                }
                break;
            case R.id.tv_shutdown_now:
                if (threadPool != null && !threadPool.isShutdown()) {
                    threadPool.shutdownNow();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pool_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_blog:
                CommonWebviewActivity.webviewEntrance(this, Constant.THREAD_POOL_BLOG);
                break;
            case R.id.item_pool_1:
                //可用于延迟执行或周期执行
                poolType = 0;
                scheduledPool = Executors.newScheduledThreadPool(4);
                tv_pool_type.setText("当前线程池类型：newScheduledThreadPool()");
                break;
            case R.id.item_pool_2:
                poolType = 1;
                threadPool = Executors.newSingleThreadExecutor();
                tv_pool_type.setText("当前线程池类型：newSingleThreadExecutor()");
                break;
            case R.id.item_pool_3:
                poolType = 2;
                threadPool = Executors.newCachedThreadPool();
                tv_pool_type.setText("当前线程池类型：newCachedThreadPool()");
                break;
            case R.id.item_pool_4:
                poolType = 3;
                threadPool = Executors.newFixedThreadPool(4);
                tv_pool_type.setText("当前线程池类型：newFixedThreadPool(4)");
                break;
        }
        return true;
    }

    //启动线程
    private void startThread(Future future, final int what) {
        if (poolType == 0) {
            if (future != null && !future.isDone() || scheduledPool.isShutdown()) return;
        } else {
            if (future != null && !future.isDone() || threadPool.isShutdown()) return;
        }

        Callable<Integer> callable = new Callable<Integer>() {
            int num = 0;

            @Override
            public Integer call() throws Exception {
                while (num < 100) {
                    num++;
                    sendMsg(num, what);
                    Thread.sleep(50);
                }
                return 100;
            }
        };
        if (poolType == 0) {
            future = scheduledPool.schedule(callable, 2, TimeUnit.SECONDS);
//            future = scheduledPool.submit(callable);
        } else {
            future = threadPool.submit(callable);
        }
        switch (what) {
            case THREAD_UPDATE_1:
                future1 = future;
                break;
            case THREAD_UPDATE_2:
                future2 = future;
                break;
            case THREAD_UPDATE_3:
                future3 = future;
                break;
            case THREAD_UPDATE_4:
                future4 = future;
                break;
            case THREAD_UPDATE_5:
                future5 = future;
                break;
        }
    }

    //停止线程
    private void stopThread(Future<Integer> future) {
        if (poolType == 0) {
            //定时线程池
            if (future != null && !scheduledPool.isShutdown()) {
                future.cancel(true);
            }
        } else {
            //其他线程池
            if (future != null && !threadPool.isShutdown()) {
                future.cancel(true);
            }
        }
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
                    tv1.setText(String.valueOf(msg.arg1 + "%"));
                    p1.setProgress(msg.arg1);
                    break;
                case THREAD_UPDATE_2:
                    tv2.setText(String.valueOf(msg.arg1 + "%"));
                    p2.setProgress(msg.arg1);
                    break;
                case THREAD_UPDATE_3:
                    tv3.setText(String.valueOf(msg.arg1 + "%"));
                    p3.setProgress(msg.arg1);
                    break;
                case THREAD_UPDATE_4:
                    tv4.setText(String.valueOf(msg.arg1 + "%"));
                    p4.setProgress(msg.arg1);
                    break;
                case THREAD_UPDATE_5:
                    tv5.setText(String.valueOf(msg.arg1 + "%"));
                    p5.setProgress(msg.arg1);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
        }
        if (threadPool != null && !threadPool.isShutdown()) {
            threadPool.shutdownNow();
        }
        if (scheduledPool != null && !scheduledPool.isShutdown()) {
            scheduledPool.shutdownNow();
        }
    }
}
