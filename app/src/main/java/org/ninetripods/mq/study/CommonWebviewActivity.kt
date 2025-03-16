package org.ninetripods.mq.study

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.KeyEvent
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.Toolbar
import org.ninetripods.mq.study.util.WebViewPool.getCachedWebView
import org.ninetripods.mq.study.util.webview.Html5Webview


class CommonWebviewActivity : BaseActivity() {
    
    private var mWebView: Html5Webview? = null
    private var url: String = ""
    private var mContainer: ViewGroup? = null
    
    override fun setContentView() {
        setContentView(R.layout.activity_common_webview)
    }

    private fun init() {
        val intent = intent
        url = intent.getStringExtra(URL_KEY) ?: ""
    }

    override fun initViews() {
        init()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        initToolBar(toolbar, "AndroidStudy", true)
        mContainer = findViewById(R.id.rl_container)
        mWebView = getCachedWebView(this)
        if (mWebView == null) {
            //没有缓存
            mWebView = Html5Webview(this)
        }
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        params.addRule(RelativeLayout.BELOW, R.id.toolbar)
        mContainer?.addView(mWebView, params)

        if (TextUtils.isEmpty(url)) {
            toast("url不能为空")
            return
        }
        mWebView?.loadUrl(url)
    }

    override fun onPause() {
        mWebView?.onPause()
        mWebView?.pauseTimers()
        super.onPause()
    }

    override fun onResume() {
        mWebView?.onResume()
        mWebView?.resumeTimers()
        super.onResume()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView?.canGoBack() == true) {
            mWebView?.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }

    companion object {
        const val URL_KEY: String = "url_key"

        /**
         * 跳转到Webview
         *
         * @param context Context
         * @param url     url地址
         */
        @JvmStatic
        fun webviewEntrance(context: Context, url: String?) {
            val intent = Intent(context, CommonWebviewActivity::class.java)
            intent.putExtra(URL_KEY, url)
            context.startActivity(intent)
        }
    }
}
