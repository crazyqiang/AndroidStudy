package org.ninetripods.mq.multiprocess_sever;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class RemoteService extends Service {
    final RemoteCallbackList<IRemoteServiceCallBack> mCallbacks = new RemoteCallbackList<>();

    public RemoteService() {
    }

    private IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public void registerCallback(IRemoteServiceCallBack cb) throws RemoteException {
            if (cb != null) mCallbacks.register(cb);
        }

        @Override
        public void unregisterCallback(IRemoteServiceCallBack cb) throws RemoteException {
            if (cb != null) mCallbacks.unregister(cb);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
