package org.ninetripods.mq.study;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;

import org.ninetripods.mq.study.util.webview.Html5Webview;

public class CommonWebviewActivity extends BaseActivity {

    private Html5Webview h5_webview;
    public static final String URL_KEY = "url_key";
    private String url;

    /**
     * 跳转到Webview
     *
     * @param context Context
     * @param url     url地址
     */
    public static void webviewEntrance(Context context, String url) {
        Intent intent = new Intent(context, CommonWebviewActivity.class);
        intent.putExtra(URL_KEY, url);
        context.startActivity(intent);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_common_webview);
    }

    private void init() {
        Intent intent = getIntent();
        url = intent.getStringExtra(URL_KEY);
    }

    @Override
    public void initViews() {
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "AndroidStudy", true);
        h5_webview = (Html5Webview) findViewById(R.id.h5_webview);
        if (TextUtils.isEmpty(url)) {
            toast("url不能为空");
            return;
        }
        h5_webview.loadUrl(url);
    }

    @Override
    protected void onPause() {
        h5_webview.onPause();
        h5_webview.pauseTimers();
        super.onPause();
    }

    @Override
    protected void onResume() {
        h5_webview.onResume();
        h5_webview.resumeTimers();
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && h5_webview.canGoBack()) {
            h5_webview.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}
