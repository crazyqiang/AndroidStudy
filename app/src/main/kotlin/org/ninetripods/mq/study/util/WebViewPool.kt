package org.ninetripods.mq.study.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.MutableContextWrapper
import android.os.Looper
import org.ninetripods.mq.study.MyApplication
import org.ninetripods.mq.study.util.webview.Html5Webview
import java.util.LinkedList

object WebViewPool {
    private const val MAX_POOL_SIZE = 2 //WebView复用池最大容量
    private val webViewStack = LinkedList<Html5Webview>()

    /**
     * 预加载WebView，在前一个页面提前初始化，传入一个Application类型的Context
     */
    fun preloadWebView() {
        Looper.myQueue().addIdleHandler {
            if (webViewStack.isEmpty()) {
                for (index in 0..MAX_POOL_SIZE) {
                    webViewStack.push(createWebView(MyApplication.getApplication()))
                }
            }
            // 在系统空闲时执行的任务，返回false，只会执行一次；如果返回true，将在每次空闲时执行
            false
        }

    }

    /**
     * 获取WebView，在使用时将Context替换为当前Activity界面的Context
     */
    fun getCachedWebView(context: Context): Html5Webview? {
        if (webViewStack.isNotEmpty()) {
            val webView = webViewStack.pop()
            //要取出来用了，重新设置Context
            (webView.context as MutableContextWrapper).baseContext = context
            return webView
        }
        return null
    }

    /**
     * 清除所有WebView
     */
    fun releaseAll() {
        if (webViewStack.isEmpty()) return
        val iterator = webViewStack.iterator()
        while (iterator.hasNext()) {
            val webView = iterator.next()
            webView.settings.javaScriptEnabled = false
            webView.destroy()
            webView.loadUrl("about:blank") // 清除内容
            //...按需处理...
        }
    }

    /**
     * 创建 WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun createWebView(context: Context): Html5Webview {
        val contextWrapper = MutableContextWrapper(context)
        return Html5Webview(contextWrapper).apply {
            settings.javaScriptEnabled = true // 启用JavaScript
            settings.domStorageEnabled = true // 启用DOM存储
            //...按需设置...
        }
    }
}
