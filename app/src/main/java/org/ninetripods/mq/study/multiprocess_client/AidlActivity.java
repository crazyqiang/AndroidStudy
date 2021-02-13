package org.ninetripods.mq.study.multiprocess_client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import org.ninetripods.mq.multiprocess_sever_i.Apple;
import org.ninetripods.mq.multiprocess_sever_i.IAidlCallBack;
import org.ninetripods.mq.multiprocess_sever_i.IRemoteService;
import org.ninetripods.mq.multiprocess_sever_i.IRemoteServiceCallBack;
import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.util.DisplayUtil;


public class AidlActivity extends BaseActivity {
    private Button btn_bind, btn_unbind;
    private Button btn_bind_common, btn_unbind_common;
    private TextView tv_info;
    private boolean isObserveBound;//注册观察者的服务是否绑定
    private IRemoteService mObserveService;//注册观察者的服务
    private boolean isCommonBound;
    private IAidlCallBack mCommonService;
    private SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_ipc_client);
    }

    @Override
    public void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "AIDL", true);
        btn_bind = (Button) findViewById(R.id.btn_bind);
        btn_unbind = (Button) findViewById(R.id.btn_unbind);
        btn_bind_common = (Button) findViewById(R.id.btn_bind_common);
        btn_unbind_common = (Button) findViewById(R.id.btn_unbind_common);
        tv_info = (TextView) findViewById(R.id.tv_info);
        showMessage("等待连接远程服务端...");
    }

    @Override
    public void initEvents() {
        btn_bind.setOnClickListener(this);
        btn_unbind.setOnClickListener(this);
        btn_bind_common.setOnClickListener(this);
        btn_unbind_common.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind:
                //绑定服务并注册观察者
                if (!isObserveBound) {
                    Intent intent = new Intent();
                    intent.setAction("android.mq.observer.service");
                    intent.setPackage("org.ninetripods.mq.multiprocess_sever");
                    bindService(intent, mObserverConnection, Context.BIND_AUTO_CREATE);
                } else {
                    showMessage("\n已经连接上啦！不用重复连接~");
                }
                break;
            case R.id.btn_unbind:
                //解除绑定并解注册观察者
                if (isObserveBound) {
                    if (mObserveService != null) {
                        //解除注册
                        try {
                            mObserveService.unregisterCallback(mCallBack);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    if (mObserverConnection != null) {
                        unbindService(mObserverConnection);
                    }
                    showMessage("\n解除绑定成功...");
                    isObserveBound = false;
                } else {
                    showMessage("\n尚未绑定...");
                }
                break;
            case R.id.btn_bind_common:
                //绑定服务
                if (!isCommonBound) {
                    Intent commonIntent = new Intent();
                    commonIntent.setAction("android.mq.common.service");
                    commonIntent.setPackage("org.ninetripods.mq.multiprocess_sever");
                    bindService(commonIntent, mCommonConnection, Context.BIND_AUTO_CREATE);
                } else {
                    showMessage("\n已经连接上啦！不用重复连接~");
                }
                break;
            case R.id.btn_unbind_common:
                //解除注册
                if (isCommonBound && mCommonConnection != null) {
                    unbindService(mCommonConnection);
                    showMessage("\n解除绑定成功...");
                    isCommonBound = false;
                } else {
                    showMessage("\n尚未绑定...");
                }
                break;
        }
    }


    private ServiceConnection mObserverConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isObserveBound = true;
            mObserveService = IRemoteService.Stub.asInterface(service);
            showMessage("\n连接远程服务端成功!", R.color.blue_color);
            try {
                mObserveService.registerCallback(mCallBack);
                showMessage("\n注册一个观察者，随时接收服务端的新通知...");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            showMessage("\n连接远程服务端失败...");
            mObserveService = null;
        }
    };
    private IRemoteServiceCallBack mCallBack = new IRemoteServiceCallBack.Stub() {
        @Override
        public void noticeAppleInfo(final Apple apple) throws RemoteException {
            if (apple != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showMessage("\n" + apple.getNoticeInfo() +
                                "\n名称：" + apple.getName() +
                                "\n最新价格：" + apple.getPrice() + " 元", R.color.red_f);
                    }
                });
            }
        }
    };

    private ServiceConnection mCommonConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            showMessage("\n连接远程服务端成功!", R.color.blue_color);
            isCommonBound = true;
            mCommonService = IAidlCallBack.Stub.asInterface(service);
            if (mCommonService != null) {
                try {
                    Apple apple = mCommonService.getAppleInfo();
                    if (apple != null) {
                        showMessage("\n" + apple.getNoticeInfo() +
                                "\n名称：" + apple.getName() +
                                "\n价格：" + apple.getPrice() + " 元", R.color.red_f);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isCommonBound = false;
            mCommonService = null;
            showMessage("\n连接远程服务端失败...");
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
}
