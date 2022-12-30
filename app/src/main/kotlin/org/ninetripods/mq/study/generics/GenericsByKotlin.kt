package org.ninetripods.mq.study.generics

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * Kotlin泛型 协变、逆变
 * Created by maqiang06@zuoyebang.com on 2022/12/21
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
     * 称为而GenericsP在T上是逆变的。
     */
    fun consume(to: GenericsC<EnglishBook>) {
//        val book: GenericsP<Book> = to
    }

    /**
     * inline + reified 使得类型参数被实化  reified:实体化的
     * 注：带reified类型参数的内联函数，Java是无法直接调用的
     */
    inline fun <reified T> isAny(value: Any): Boolean {
        return value is T
    }

    /**
     * 1、如果A是B的子类，且Producer<A>是Producer<B>的子类型，那么这个泛型是协变的；
     * 2、如果A是B的子类，且Consumer<B>是Consumer<A>的子类型，那么这个泛型是逆变的。
     */
    fun addContent(list: ArrayList<Any>) {
        list.add(40)
    }
}

/**
 * 通过reified实化类型参数来简化调用
 */
inline fun <reified T : Activity> Context.startActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
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