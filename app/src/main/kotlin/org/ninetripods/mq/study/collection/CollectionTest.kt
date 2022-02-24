package org.ninetripods.mq.study.collection

/**
 * 集合相关操作
 */
interface Shape {}
class Rectangle : Shape
class Circle : Shape
object CollectionTest {

    //TODO
    fun mapTest() {
        val numMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3)
        println(numMap.keys) //[key1, key2, key3]
        println(numMap.values) //[1, 2, 3]
        println(numMap.entries) //[key1=1, key2=2, key3=3]
        println(numMap["key1"]) // 1
        println("${numMap.containsValue(1)}, ${1 in numMap.values}") //true true
        println("${numMap.containsKey("key1")}, ${"key1" in numMap.keys}") //true true
    }

    fun runListTest() {

        val models = setOf<Shape>()
//        models.add(Rectangle())
//        models.add(Circle())

        LinkedHashMap<String, String>()
        HashMap<String, String>()
        //val modelList: List<String> = MutableList<String>()
        val modelList: List<String> = mutableListOf()

        modelList.shuffled()
        val numbersMap = mutableMapOf("one" to 1, "two" to 2)
        val numbersMap1 = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)
    }
}