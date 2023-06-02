package org.ninetripods.mq.study.function

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 委托模式 -> 类委托、属性委托
 * Created by mq on 2023/5/30
 */
//class DelegateKt(delegate: ISay) : ISay by delegate

fun main() {
    val clz = Example()
    println(clz.str)
    clz.str = "heheda"
}

class Example {
    var str: String by DelegateRW()
}

/**
 * 属性的委托类
 */
class Delegate {
    /**
     * 对应属性中的get()，表示获取数据
     * @param thisRef
     * @param property
     */
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef,${property.name}"
    }

    /**
     * 对应属性中的set()，表示设置数据，只有var的属性会有
     * @param thisRef
     * @param property
     * @param value
     */
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$thisRef , ${property.name} , $value")
    }
}

/**
 * 对于声明的val属性，只有getValue()
 */
class DelegateR : ReadOnlyProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return "$thisRef,${property.name}"
    }
}

/**
 * 对于声明的var属性，getValue()/setValue()都有
 */
class DelegateRW : ReadWriteProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return "$thisRef,${property.name}"
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        println("$thisRef , ${property.name} , $value")
    }
}

///**
// * 类委托
// */
//class DelegateImp : ISay {
//    override fun sayHello() {
//        println("sayHello from DelegateImp")
//    }
//}
//
//class RealSubject : ISay by DelegateImp()
//
//interface ISay {
//    fun sayHello()
//}