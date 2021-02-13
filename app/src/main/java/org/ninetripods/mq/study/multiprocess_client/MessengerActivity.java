package org.ninetripods.mq.study.multiprocess_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.util.DisplayUtil;


public class MessengerActivity extends BaseActivity {
    private Messenger mService;
    private Button btn_bind, btn_unbind;
    private static final int MESS_FROM_CLIENT = 0x0707;
    private static final int ServerThinking = 0x0708;
    private static final int MESS_FROM_SERVER = 0x0709;
    private static final String RESPONSE_KEY = "response_key";
    private TextView tv_info;
    private SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
    private boolean isBound;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_messager);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "Messenger", true);
        btn_bind = (Button) findViewById(R.id.btn_bind);
        btn_unbind = (Button) findViewById(R.id.btn_unbind);
        tv_info = (TextView) findViewById(R.id.tv_info);
        showMessage("等待连接远程服务端...");
    }

    @Override
    public void initEvents() {
        btn_bind.setOnClickListener(this);
        btn_unbind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_bind:
                //绑定服务 利用Messenger进行通信
                if (!isBound) {
                    Intent intent = new Intent();
                    intent.setAction("android.mq.messenger.service");
                    intent.setPackage("org.ninetripods.mq.multiprocess_sever");
                    bindService(intent, connection, BIND_AUTO_CREATE);
                } else {
                    showMessage("\n已经连接上啦！不用重复连接~");
                }
                break;
            case R.id.btn_unbind:
                //解除绑定
                if (isBound) {
                    unbindService(connection);
                    showMessage("\n解除绑定成功...");
                    isBound = false;
                } else {
                    showMessage("\n尚未绑定...");
                }
                break;
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            showMessage("\n连接远程服务端成功!", R.color.blue_color);
            isBound = true;
            mService = new Messenger(service);
            String question = "\n客户端：程序猿最讨厌皇帝的第几个儿子？";
            Message message = Message.obtain(null, MESS_FROM_CLIENT);
            //Messenger用来接收服务端发来的信息
            message.replyTo = messenger;
            try {
                //将Message发送到服务端
                mService.send(message);
                showMessage(question, R.color.red_f);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            isBound = false;
        }
    };

    Messenger messenger = new Messenger(new ClientHandler());

    private class ClientHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ServerThinking:
                    showMessage("\n服务器正在绞尽脑汁的思考...");
                    break;
                case MESS_FROM_SERVER:
                    //接收服务器传过来的值
                    Bundle bundle = msg.getData();
                    String responseInfo = bundle.getString(RESPONSE_KEY);
                    if (!TextUtils.isEmpty(responseInfo)) {
                        showMessage("\n" + responseInfo, R.color.red_f);
                    }
                    break;
            }
        }
    }

    /**
     * 显示文字
     *
     * @param info 提示信息
     */
    private void showMessage(String info) {
        showMessage(info, R.color.black_deep);
    }

    /**
     * 显示文字
     *
     * @param info 提示信息
     */
    private void showMessage(String info, int color) {
        int startPos = stringBuilder.length();
        stringBuilder.append(info);
        tv_info.setText(DisplayUtil.changeTextColor(this, stringBuilder, color, startPos));
    }

}
