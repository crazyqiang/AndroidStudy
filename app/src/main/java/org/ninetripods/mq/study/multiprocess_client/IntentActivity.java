package org.ninetripods.mq.study.multiprocess_client;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.ninetripods.mq.multiprocess_sever_i.Apple;
import org.ninetripods.mq.multiprocess_sever_i.MultiProcess;
import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;

public class IntentActivity extends BaseActivity {
    private Button btn_send;
    private EditText et_input;
    private static final String STRING_KEY = "string_key";
    private static final String OBJECT_KEY = "object_key";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_intent);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "Intent", true);
        btn_send = (Button) findViewById(R.id.btn_send);
        et_input = (EditText) findViewById(R.id.et_input);
    }

    @Override
    public void initEvents() {
        btn_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                String getStr = et_input.getText().toString();
                if (TextUtils.isEmpty(getStr)) {
                    toast("请输入文字~");
                    return;
                }
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.mq.intent");
                    Bundle bundle = new Bundle();
                    bundle.putString(STRING_KEY, "利用Intent执行IPC过程的一次通信，下面是另一个进程发来的消息:");
                    bundle.putParcelable(OBJECT_KEY, new MultiProcess(getStr));
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    toast("请先启动服务端进程~");
                }
                break;
        }
        super.onClick(v);
    }


}
