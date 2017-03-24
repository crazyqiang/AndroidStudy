package org.ninetripods.mq.study.multiprocess_client;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.ninetripods.mq.multiprocess_sever.Apple;
import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.util.DisplayUtil;


public class BinderActivity extends BaseActivity {
    private static final java.lang.String DESCRIPTOR = "org.ninetripods.mq.multiprocess_sever.IAidlCallBack";
    private static final int KEY_FLAG = 0x110;
    private Button btn_bind, btn_unbind;
    private TextView tv_info;
    private SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
    private boolean isBound;
    private IBinder mService;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_binder);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "Binder", true);
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
        switch (v.getId()) {
            case R.id.btn_bind:
                //绑定服务 利用Messenger进行通信
                if (!isBound) {
                    Intent intent = new Intent();
                    intent.setAction("android.mq.binder.service");
                    intent.setPackage("org.ninetripods.mq.multiprocess_sever");
                    bindService(intent, binderConnection, BIND_AUTO_CREATE);
                } else {
                    showMessage("\n已经连接上啦！不用重复连接~");
                }
                break;
            case R.id.btn_unbind:
                //解除绑定
                if (isBound) {
                    unbindService(binderConnection);
                    showMessage("\n解除绑定成功...");
                    isBound = false;
                } else {
                    showMessage("\n尚未绑定...");
                }
                break;
        }
        super.onClick(v);
    }

    private ServiceConnection binderConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBound = true;
            mService = service;
            showMessage("\n连接远程服务端成功!", R.color.blue_color);
            if (mService != null) {
                //声明两个Parcel类型数据(_data和_reply) 一个用于传输数据 一个用于接收数据
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                Apple apple;
                try {
                    //与服务器端的enforceInterface(DESCRIPTOR)对应
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mService.transact(KEY_FLAG, _data, _reply, 0);
                    _reply.readException();
                    if (0 != _reply.readInt()) {
                        apple = Apple.CREATOR.createFromParcel(_reply);
                    } else {
                        apple = null;
                    }
                    showMessage(apple != null ? ("\n" + apple.getNoticeInfo() + "\n名称："
                            + apple.getName() + "\n价格：" + apple.getPrice() + " 元") : "未获得服务器信息", R.color.red_f);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    _data.recycle();
                    _reply.recycle();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            mService = null;
        }
    };

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binderConnection != null) {
            unbindService(binderConnection);
            binderConnection = null;
        }
    }
}
