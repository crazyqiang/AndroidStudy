package org.ninetripods.mq.study.function

import kotlin.properties.Delegates
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
//    RealImp().sayHello() //方式1
//    RealImp2().sayHello() //方式2
//    RealImp3(DelegateImp()).sayHello() //方式3

    /**
     * 2、属性委托
     */
    val property = DelegateProperty()
    //p1
//    println(property.p1)
//    property.p1 = "小马快跑"
//    //p2
//    println(property.p2)
//    //p3
//    println(property.p3)
//    property.p3 = "小马快跑"

    /**
     * by lazy延迟委托
     */
//    println("第1次：${property.lazyView}")
//    println("第2次：${property.lazyView}")

    /**
     * 模拟通过Delegates.observable()来实现防抖动功能，实际在Activity中通过onClick()实现
     *  override fun onClick(view: View) {
     *     lastClickTime = System.currentTimeMillis()
     *   }
     */
//    property.lastClickTime = System.currentTimeMillis()
//    Thread.sleep(1000) //两次点击时间必须超过1s，否则下面的
//    property.lastClickTime = System.currentTimeMillis()

    /**
     *Delegates.vetoable()中的lambda表达式的返回值决定是否生效本次赋值。
     */
//    property.age = "二年级"
//    property.age = "三年级"

    /**
     * ::来实现属性之间的委托
     */
    property.oldName = "2023"
    println("oldName: ${property.oldName}")
    println("newName: ${property.newName}")
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

    //延迟属性，默认模式是LazyThreadSafetyMode.SYNCHRONIZED
    val lazyView by lazy {
        println("我只有第一次初始化的时候才执行哟~")
        "Come on"
    }

    //lazy(LazyThreadSafetyMode.NONE
    /**
     * @mode:
     * 1、LazyThreadSafetyMode.SYNCHRONIZED
     * 2、LazyThreadSafetyMode.PUBLICATION
     * 3、LazyThreadSafetyMode.NONE
     *
     */
    val lazyMode by lazy(LazyThreadSafetyMode.NONE) {
        "Lazy Mode"
    }

    /**
     * Delegates.observable()
     */
    var lastClickTime by Delegates.observable(0L) { property, oldValue, newValue ->
        // 在lastClickTime属性值发生变化时，判断两次点击时间的间隔是否大于1秒，如果是，则执行点击事件。
        if (newValue - oldValue >= 1000) {
            println("执行点击事件： $oldValue, $newValue")
            //执行点击事件
        }
    }

    /**
     * Delegates.vetoable()中的lambda表达式的返回值决定是否生效本次赋值。
     */
    var age: String by Delegates.vetoable("一年级") { property, oldValue, newValue ->
        println("$property, $oldValue, $newValue")
        true
    }

    @Deprecated("Use [newName] instead", ReplaceWith("newName"))
    var oldName by this::newName //this可以省略
    var newName = ""
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