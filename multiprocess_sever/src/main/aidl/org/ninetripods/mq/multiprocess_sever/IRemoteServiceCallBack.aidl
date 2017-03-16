// IRemoteServiceCallBack.aidl
package org.ninetripods.mq.multiprocess_sever;

import org.ninetripods.mq.multiprocess_sever.Apple;
// Declare any non-default types here with import statements

 interface IRemoteServiceCallBack {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void getAppleInfo(inout Apple apple);
}
