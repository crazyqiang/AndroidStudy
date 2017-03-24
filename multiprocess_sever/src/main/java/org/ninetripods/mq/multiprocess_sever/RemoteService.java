package org.ninetripods.mq.multiprocess_sever;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class RemoteService extends Service {
    public RemoteService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private IAidlCallBack.Stub mBinder = new IAidlCallBack.Stub() {
        @Override
        public Apple getAppleInfo() throws RemoteException {
            return new Apple("蛇果", 20f, getString(R.string.respose_info));
        }
    };
}
