// IRemoteServiceCallBack.aidl
package org.ninetripods.mq.multiprocess_sever_i;

import org.ninetripods.mq.multiprocess_sever_i.Apple;
// Declare any non-default types here with import statements

 interface IRemoteServiceCallBack {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void noticeAppleInfo(inout Apple apple);
}
