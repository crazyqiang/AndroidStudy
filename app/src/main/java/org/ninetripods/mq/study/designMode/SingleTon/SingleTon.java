package org.ninetripods.mq.study.designMode.SingleTon;

/**
 * 单例模式
 * Created by MQ on 2017/4/23.
 */

public class SingleTon {

    //1、简单粗暴式
    private static SingleTon uniqueInstance;

    private SingleTon() {
    }

    public static synchronized SingleTon getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SingleTon();
        }
        return uniqueInstance;
    }

    //2、饿汉式
//    private static SingleTon uniqueInstance = new SingleTon();
//    private SingleTon() {
//    }
//    public static SingleTon getInstance() {
//        return uniqueInstance;
//    }
    //3、懒汉式(双重枷锁式)
//    private volatile static SingleTon uniqueInstance;
//    private SingleTon() {
//    }
//    public static SingleTon getInstance() {
//        if (uniqueInstance == null) {
//            synchronized (SingleTon.class) {
//                if (uniqueInstance == null) {
//                    uniqueInstance = new SingleTon();
//                }
//            }
//        }
//        return uniqueInstance;
//    }
}
