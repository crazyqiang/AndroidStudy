package org.ninetripods.mq.study.generics

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
    }
}

/**
 * 泛型类
 */
class Transport<T>(t: T) {

}

open class Book(val name: String)
data class ChineseBook(val chinese: String) : Book(chinese)
data class MathBook(val math: String) : Book(math)