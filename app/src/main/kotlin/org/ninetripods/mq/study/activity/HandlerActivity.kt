package org.ninetripods.mq.study.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference

/**
 * Kotlin中如何使用Handler
 */
class HandlerActivity : AppCompatActivity() {
    companion object {
        const val WHAT_HINT_TEXT = 1000 //MSG_WHAT
    }

    private val mOutPut = "我输出了" //成员变量
    private val weakHandler by lazy { WeakReferenceHandler(this) }

    //static + 弱引用
    class WeakReferenceHandler(obj: HandlerActivity) : Handler(Looper.getMainLooper()) {
        private val mRef: WeakReference<HandlerActivity> = WeakReference(obj)


        override fun handleMessage(msg: Message) {
            mRef.get()?.run {
                when (msg.what) {
                    WHAT_HINT_TEXT -> println(mOutPut) //可以直接访问Activity中的变量
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weakHandler.sendEmptyMessageDelayed(WHAT_HINT_TEXT, 5000)
    }

    override fun onDestroy() {
        //退出页面时，置空所以的Message
        weakHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}