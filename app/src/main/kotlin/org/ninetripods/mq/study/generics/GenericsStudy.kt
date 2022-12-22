package org.ninetripods.mq.study.generics

class GenericsStudy {

    fun feedAll(animals: Herd<Animal>) {
        for (i in 0 until animals.size) {
            animals.getVa().feed()
        }
    }

    fun feedCatHerd(cats: Herd<Cat>) {
        for (i in 0 until cats.size) {
            cats.getVa().clean()
        }
        feedAll(cats)
    }

    fun startSort() {
        val anyComparator = Comparator<Any> { o1, o2 -> o1.hashCode() - o2.hashCode() }
        val numberComparator = Comparator<Number> { o1, o2 -> o1.toInt() - o2.toInt() }
        val intComparator = Comparator<Int> { o1, o2 -> o1.toInt() - o2.toInt() }
        val nums: List<Number> = arrayListOf(10, 100, 1000)
        nums.sortedWith(anyComparator)
        nums.sortedWith(numberComparator)
        //nums.sortedWith(intComparator)
    }

}

fun <T> Iterable<T>.sortedWith(comparator: Comparator<in T>): List<T> {
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

    val size = 10
}

