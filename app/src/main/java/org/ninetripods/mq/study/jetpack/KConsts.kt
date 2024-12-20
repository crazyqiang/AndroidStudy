package org.ninetripods.mq.study.jetpack

import androidx.annotation.IntDef
/**
 * Created by mq on 2021/6/28 下午11:12
 * mqcoder90@gmail.com
 */
object KConsts {

    const val FRAGMENT_HOME = 0 //Home
    const val FRAGMENT_JETPACK = 1 //Jetpack
    const val FRAGMENT_JETPACK_COMPOSE = 2 //Jetpack Compose
    const val FRAGMENT_POP = 3 //Pop
    const val FRAGMENT_PROCESS = 4 //多进程
    const val FRAGMENT_RECYCLERVIEW = 5 //RecyclerView
    const val FRAGMENT_MULTI_THREAD = 6 //多线程
    const val FRAGMENT_NESTED_SCROLLER = 7 //嵌套滑动
    const val FRAGMENT_VIEW = 8 //View相关

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