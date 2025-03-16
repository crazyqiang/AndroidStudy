package org.ninetripods.mq.study.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.MutableContextWrapper
import org.ninetripods.mq.study.MyApplication
import org.ninetripods.mq.study.util.webview.Html5Webview
import java.util.LinkedList

object WebViewPool {
    private const val MAX_POOL_SIZE = 2 // WebView 复用池最大容量
    private val webViewStack = LinkedList<Html5Webview>()

    /**
     * 预加载WebView，在前一个页面提前初始化
     */
    fun preloadWebView() {
        if (webViewStack.isEmpty()) {
            for (index in 0..MAX_POOL_SIZE) {
                webViewStack.push(createWebView(MyApplication.getApplication()))
            }
        }
    }

    /**
     * 获取 WebView
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

    fun releaseAll() {
        if (webViewStack.isEmpty()) return
        webViewStack.forEach { webView ->
            webView.settings.javaScriptEnabled = false
            webView.destroy()
            webView.loadUrl("about:blank") // 清除内容
        }
    }

    /**
     * 创建 WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun createWebView(context: Context): Html5Webview {
        val contextWrapper = MutableContextWrapper(context)
        return Html5Webview(contextWrapper).apply {
            settings.javaScriptEnabled = true // 启用 JavaScript
            settings.domStorageEnabled = true // 启用 DOM 存储
        }
    }
}
