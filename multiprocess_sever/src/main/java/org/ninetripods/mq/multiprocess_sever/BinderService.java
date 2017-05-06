package org.ninetripods.mq.multiprocess_sever;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import org.ninetripods.mq.multiprocess_sever_i.Apple;

public class BinderService extends Service {
    private static final java.lang.String DESCRIPTOR = "org.ninetripods.mq.multiprocess_sever.IAidlCallBack";
    private static final int KEY_FLAG = 0x110;

    public BinderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    private class MyBinder extends Binder {
        /**
         * @param code  唯一标识，客户端传递标识执行服务端代码
         * @param data  客户端传递过来的参数
         * @param reply 服务器返回回去的值
         * @param flags 是否有返回值 0:有 1:没有
         * @return
         * @throws RemoteException 异常
         */
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case KEY_FLAG:
                    //标识服务器名称
                    data.enforceInterface(DESCRIPTOR);
                    Apple apple = new Apple("红星苹果", 15f, getString(R.string.response_binder_info));
                    reply.writeNoException();
                    reply.writeInt(1);
                    apple.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    return true;
            }

            return super.onTransact(code, data, reply, flags);
        }
    }

}
