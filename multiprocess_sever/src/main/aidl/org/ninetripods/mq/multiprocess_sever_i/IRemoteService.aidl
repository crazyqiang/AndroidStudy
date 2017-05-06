// IRemoteService.aidl
package org.ninetripods.mq.multiprocess_sever_i;

import org.ninetripods.mq.multiprocess_sever_i.IRemoteServiceCallBack;
// Declare any non-default types here with import statements

interface IRemoteService {
    /**
         * Often you want to allow a service to call back to its clients.
         * This shows how to do so, by registering a callback interface with
         * the service.
         */
        void registerCallback(IRemoteServiceCallBack cb);

        /**
         * Remove a previously registered callback interface.
         */
        void unregisterCallback(IRemoteServiceCallBack cb);
}
