package org.ninetripods.mq.study.multiThread.asynctask;


import android.os.AsyncTask;

import java.util.concurrent.Executor;

/**
 * 基于AsyncTask的多线程封装
 * Created by mq on 2020/5/30 下午10:55
 * mqcoder90@gmail.com
 */
public class Scheduler {

    public interface TaskRunnable {
        //在子线程中执行
        void executeOnThread();

        //在主线程中执行
        void executeOnMainThread();
    }

    /**
     * AsyncTask线程池—串行
     */
    public static Executor serialExecutor() {
        return AsyncTask.SERIAL_EXECUTOR;
    }

    /**
     * AsyncTask线程池—并行
     */
    public static Executor threadPoolExecutor() {
        return AsyncTask.THREAD_POOL_EXECUTOR;
    }

    public static void asyn(Runnable runnable) {
        asyn(threadPoolExecutor(), runnable);
    }

    public static void asyn(Executor executor, Runnable runnable) {
        new AsyncTask0().executeOnExecutor(executor, runnable);
    }

    public static void asyn(TaskRunnable task) {
        asyn(threadPoolExecutor(), task);
    }

    public static void asyn(Executor executor, TaskRunnable task) {
        new AsyncTask1().executeOnExecutor(executor, task);
    }


    private static class AsyncTask0 extends AsyncTask<Runnable, Void, Void> {
        @Override
        protected Void doInBackground(Runnable... runnables) {
            runnables[0].run();
            return null;
        }
    }

    private static class AsyncTask1<T> extends AsyncTask<TaskRunnable, T, TaskRunnable> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected TaskRunnable doInBackground(TaskRunnable... runnables) {
            runnables[0].executeOnThread();
            return null;
        }

        @SafeVarargs
        @Override
        protected final void onProgressUpdate(T... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(TaskRunnable taskRunnable) {
            if (taskRunnable != null) {
                taskRunnable.executeOnThread();
            }
        }
    }


}
