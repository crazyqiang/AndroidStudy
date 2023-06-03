package org.ninetripods.mq.study.function

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 委托模式 -> 接口类委托、属性委托
 * Created by mq on 2023/5/30
 */
fun main() {
    /**
     * 1、类委托
     */
    RealImp().sayHello() //方式1
    RealImp2().sayHello() //方式2
    RealImp3(DelegateImp()).sayHello() //方式3

    /**
     * 2、属性委托
     */
    val property = DelegateProperty()
    //p1
    println(property.p1)
    property.p1 = "小马快跑"
    //p2
    println(property.p2)
    //p3
    println(property.p3)
    property.p3 = "小马快跑"
}

/**
 * ------------------------类委托------------------------start
 */
interface ISay {
    fun sayHello()
}

class DelegateImp : ISay {
    override fun sayHello() {
        println("sayHello from DelegateImp")
    }
}

class RealImp : ISay {
    //来自DelegateImp代理对象
    private val delegate = DelegateImp()
    override fun sayHello() {
        delegate.sayHello()
    }
}

/**
 * 上述[RealImp]的实现方式可以直接通过下面的方式来替代
 */
class RealImp2 : ISay by DelegateImp() {
    /**
     * 如果有需要，[DelegateImp]的sayHello()还可以被复写
     */
//    override fun sayHello() {
//        println("哎呀，我又被复写了！")
//    }
}

/**
 * 上述[RealImp]的实现方式可以直接通过下面的方式来替代
 */
class RealImp3(delegate: ISay) : ISay by delegate
/**
 * ------------------------类委托------------------------end
 */

/**
 * ------------------------属性委托------------------------start
 */
class DelegateProperty {
    var p1: String by Delegate()
    val p2: String by DelegateR()
    var p3: String by DelegateRW()
}

/**
 * 属性的委托类
 */
class Delegate {
    /**
     * 对应属性中的get()，表示获取数据
     * @param thisRef 请求其值的对象，即被委托对象的实例
     * @param property 被委托对象属性的元数据。包含了被委托对象的属性的名称、类型、可见性等信息。
     * 通过property对象，我们可以获取被委托对象的属性值，并进行相应的操作。
     * @return 返回property value
     */
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef，${property.name}"
    }

    /**
     * 对应属性中的set()，表示设置数据，只有var的属性会有
     * @param thisRef 同上
     * @param property 同上
     * @param value 要设置的值
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
        return "getValue：$thisRef，${property.name}"
    }
}

/**
 * 对于声明的var属性，getValue()/setValue()都有
 */
class DelegateRW : ReadWriteProperty<Any, String> {
    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return "getValue：$thisRef，${property.name}"
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        println("setValue：$thisRef，${property.name}，$value")
    }
}
/**
 * ------------------------属性委托------------------------end
 */