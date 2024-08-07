package org.ninetripods.mq.study.activity

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.ninetripods.mq.study.kotlin.ktx.log
import java.lang.ref.WeakReference

/**
 * 1、Kotlin中如何使用Handler
 * 2、IdleHandler
 */
class HandlerActivity : AppCompatActivity() {
    companion object {
        const val WHAT_HINT_TEXT = 1000 //MSG_WHAT
        const val WHAT_MSG_SYNC = 2000
    }

    private val mOutPut = "Handler测试：我输出了" //成员变量
    private var idleNum = 0
    private val weakHandler by lazy { WeakReferenceHandler(this) }

    //static + 弱引用
    class WeakReferenceHandler(obj: HandlerActivity) : Handler(Looper.getMainLooper()) {
        private val mRef: WeakReference<HandlerActivity> = WeakReference(obj)


        override fun handleMessage(msg: Message) {
            mRef.get()?.run {
                when (msg.what) {
                    WHAT_HINT_TEXT -> log(mOutPut) //可以直接访问Activity中的变量
                    WHAT_MSG_SYNC -> log("同步屏障，异步消息先执行！")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //1、同步消息
        weakHandler.sendEmptyMessageDelayed(WHAT_HINT_TEXT, 3000)

        //2、异步消息
        /**
         * NOTE: 高版本API已经不支持直接调用postSyncBarrier/removeSyncBarrier了
         * weakHandler.looper.queue.postSyncBarrier()
         * weakHandler.looper.queue.removeSyncBarrier()
         * 如果不设置同步屏障，即：message.target == null，那么只把message设置成异步消息是不生效的
         */
        val asyncMsg = Message.obtain().apply {
            what = WHAT_MSG_SYNC
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                isAsynchronous = true
            }
        }
        weakHandler.sendMessageDelayed(asyncMsg, 5000)

        //3、IdleHandler空闲消息
        processOnIdleHandler()
    }

    /**
     * 处理IdleHandler
     */
    private fun processOnIdleHandler() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val queue = Looper.getMainLooper().queue //messageQueue
            queue.addIdleHandler {
                runBlocking {
                    delay(1000)
                }
                idleNum ++
                log("IdleHandler执行 ====> $idleNum")
                false
            }
        }
    }

    override fun onDestroy() {
        //退出页面时，置空所以的Message
        weakHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}