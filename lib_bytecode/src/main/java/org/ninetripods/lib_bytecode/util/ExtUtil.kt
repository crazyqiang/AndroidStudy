package org.ninetripods.lib_bytecode.util

import org.ninetripods.lib_bytecode.asm.extension.MethodExt

/**
 * Created by mq on 2023/2/21
 */
object ExtUtil {
    val methodExt = MethodExt() //函数插桩Ext设置

    fun processMethodStart(){}

    fun processMethodEnd(thresholdTime:Int,methodName:String){

    }
}