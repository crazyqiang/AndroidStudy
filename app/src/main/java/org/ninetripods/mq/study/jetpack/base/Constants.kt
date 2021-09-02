package org.ninetripods.mq.study.jetpack.base

import androidx.annotation.IntDef

class Constants {

    //指定注解的保留策略，AnnotationRetention.SOURCE表示只保留源码中，编译时删除。还有CLASS和RUNTIME
    @Retention(AnnotationRetention.SOURCE)
    //定义int值
    @IntDef(value = [StateEmpty, StateError])
    //定义注解类型
    annotation class State
    companion object {
        const val StateEmpty = 0
        const val StateError = -1
    }

}