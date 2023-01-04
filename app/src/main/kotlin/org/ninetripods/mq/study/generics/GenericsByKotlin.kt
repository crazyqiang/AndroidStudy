package org.ninetripods.mq.study.generics

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * 泛型类
 */
abstract class BaseBook<T> {
    private var books: ArrayList<T> = ArrayList()

    /**
     * 泛型方法
     */
    fun <E : T> add(item: E) {
        books.add(item)
        println("list:$books, size:${books.size}")
    }
}

/**
 * 子类继承BaseBook并传入泛型参数MathBook
 */
class BookImpl : BaseBook<MathBook>()

fun show() {
    BookImpl().apply {
        add(MathBook("数学"))
    }
    //输出：list:[MathBook(math=数学)], size: 1
}

/**
 * Kotlin泛型 协变、逆变
 * 1、如果A是B的子类，且Producer<A>是Producer<B>的子类型，那么Producer在<T>上是协变的；
 * 2、如果A是B的子类，且Consumer<B>是Consumer<A>的子类型，那么Producer在<T>上是逆变的。
 */
class GenericsByKotlin {

    /**
     * EnglishBook是Book的子类，而GenericsP<out T>因为声明了out，所以GenericsP<EnglishBook>也是GenericsP<Book>的子类，
     * 称为而GenericsP在T上是协变的。
     */
    fun produce(from: GenericsP<EnglishBook>) {
        val book: GenericsP<Book> = from
    }

    /**
     * 称为GenericsC在Book上是逆变的。
     * 跟系统源码中的Comparable类似
     */
    private fun consume(to: GenericsC<Book>) {
        //GenericsC<Book>实例赋值给了GenericsC<EnglishBook>
        val target: GenericsC<EnglishBook> = to
        target.put(EnglishBook("英语"))
    }

    /**
     * 使用处型变：类型投影
     */
    fun copy(from: Array<out Any>, to: Array<Any>) {
        if (from.size != to.size) return
        for (i in from.indices)
            to[i] = from[i]
    }

    /**
     * 使用处型变：类型投影
     */
    fun processCopy() {
        val strs: Array<String> = arrayOf("1", "2")
        val any = Array<Any>(2) {}
        copy(strs, any)
    }

    /**
     * inline + reified 使得类型参数被实化  reified:实体化的
     * 注：带reified类型参数的内联函数，Java是无法直接调用的
     */
    inline fun <reified T> isAny(value: Any): Boolean {
        return value is T
    }

    /**
     * 通过reified实化类型参数来简化调用
     */
    inline fun <reified T : Activity> Context.startActivity() {
        val intent = Intent(this, T::class.java)
        startActivity(intent)
    }
}

/**
 * PECS —— 生产者：Extends, 消费者：Super
 */
interface GenericsP<out T> {
    fun get(): T  //读取并返回T，可以认为只能读取T的对象是生产者
    // fun put(item: T) //错误，不允许在输入参数中使用
}

interface GenericsC<in T> {
    fun put(item: T) //写入T，可以认为只能写入T的对象是消费者
    //fun get(): T  //错误，不允许在返回中使用
}

open class Book(val name: String)
data class EnglishBook(val english: String) : Book(english)
data class MathBook(val math: String) : Book(math)