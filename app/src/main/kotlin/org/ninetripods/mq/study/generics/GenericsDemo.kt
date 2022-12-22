package org.ninetripods.mq.study.generics

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * Kotlin泛型 协变、逆变
 * Created by maqiang06@zuoyebang.com on 2022/12/21
 */
class GenericsDemo {

    /**
     * 泛型方法
     */
    fun <T> generics(t: T) {
        val book: Book = ChineseBook("语文")
        val chineseBooks: List<Book> = ArrayList<ChineseBook>()
        chineseBooks[0]
//        val contents = arrayListOf("2", "34")
//        addContent(contents)
    }

    /**
     * inline + reified 使得类型参数被实化  reified:实体化的
     * 注：带reified类型参数的内联函数，Java是无法直接调用的
     */
    inline fun <reified T> isAny(value: Any): Boolean {
        return value is T
    }

    /**
     * 1、如果A是B的子类，且Producer<A>是Producer<B>的子类型，那么这个类是协变的；
     * 2、如果A是B的子类，且Consumer<B>是Consumer<A>的子类型，那么这个类是逆变的。
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
 * 泛型类
 */
class Transport<T>(t: T) {

}

open class Book(val name: String)
data class ChineseBook(val chinese: String) : Book(chinese)
data class MathBook(val math: String) : Book(math)