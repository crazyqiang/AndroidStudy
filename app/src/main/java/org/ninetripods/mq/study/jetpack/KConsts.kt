package org.ninetripods.mq.study.jetpack

import androidx.annotation.IntDef
/**
 * Created by mq on 2021/6/28 下午11:12
 * mqcoder90@gmail.com
 */
object KConsts {

    const val FRAGMENT_HOME = 0 //Home
    const val FRAGMENT_JETPACK = 1 //Jetpack
    const val FRAGMENT_POP = 2 //Pop
    const val FRAGMENT_PROCESS = 3 //多进程
    const val FRAGMENT_RECYCLERVIEW = 4 //RecyclerView
    const val FRAGMENT_MULTI_THREAD = 5 //多线程
    const val FRAGMENT_NESTED_SCROLLER = 6 //嵌套滑动

    const val LIFE_TAG: String = "SERVICE"
    const val LIFE_APPLICATION_TAG: String = "Lifecycle_App_Study"
    const val ACTIVITY: String = "ACTIVITY"
    const val SERVICE: String = "SERVICE"
    const val FRAGMENT: String = "FRAGMENT"
    const val LIVE_DATA: String = "LIVEDATA"
    const val VIEW_MODEL: String = "VIEW_MODEL"

    const val StateEmpty = 0
    const val StateError = -1
    //指定注解的保留策略，AnnotationRetention.SOURCE表示只保留源码中，编译时删除。还有CLASS和RUNTIME
    @Retention(AnnotationRetention.SOURCE)
    //定义int值
    @IntDef(value = [StateEmpty, StateError])
    //定义注解类型
    annotation class State
}