package org.ninetripods.mq.study.generics



//    fun feedAll(animals: Herd<Animal>) {
//        for (i in 0 until animals.size) {
//            animals.getVa().feed()
//        }
//    }
//
//    fun feedCatHerd(cats: Herd<Cat>) {
//        for (i in 0 until cats.size) {
//            cats.getVa().clean()
//        }
//        feedAll(cats)
//    }
//
//    fun startSort() {
//        val anyComparator = Comparator<Any> { o1, o2 -> o1.hashCode() - o2.hashCode() }
//        val numberComparator = Comparator<Number> { o1, o2 -> o1.toInt() - o2.toInt() }
//        val intComparator = Comparator<Int> { o1, o2 -> o1.toInt() - o2.toInt() }
//        val nums: List<Number> = arrayListOf(10, 100, 1000)
//        nums.sortedWith(anyComparator)
//        nums.sortedWith(numberComparator)
////        nums.sortedWith(intComparator)
//    }
//
////    interface Comparable<in T> {
////        operator fun compareTo(other: T): Int
////    }
//
//    fun demo(x: Comparable<Number>) {
////        x.compareTo(1.0) // 1.0 拥有类型 Double，它是 Number 的子类型
//        // 因此，我们可以将 x 赋给类型为 Comparable <Double> 的变量
//        val y: Comparable<Double> = x // OK！
//    }

//}

fun <T> Iterable<T>.sortedWith(comparator: Comparator<T>): List<T> {
    if (this is Collection) {
        if (size <= 1) return this.toList()
        return (toTypedArray<Any?>() as Array<T>).apply { sortWith(comparator) }.asList()
    }
    return toMutableList().apply { sortWith(comparator) }
}

open class Animal {
    fun feed() {
        println("animal feed")
    }
}

class Cat : Animal() {
    fun clean() {
        println("clean")
    }
}

class Herd<out T : Animal> {
    val animal = Animal()

    fun getVa(): T {
        return animal as T
    }

//    fun setVa(value: T){
//
//    }

    val size = 10
}

