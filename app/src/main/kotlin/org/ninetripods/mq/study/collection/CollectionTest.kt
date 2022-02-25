package org.ninetripods.mq.study.collection

/**
 * 集合相关操作
 * 线上kotlin调试：https://play.kotlinlang.org/
 * 集合相关操作：https://www.kotlincn.net/docs/reference/collections-overview.html
 */
object CollectionTest {

    /**
     * 集合测试
     */
    fun collectionTest() {
        listTest() //List
        setTest() //Set
        mapTest() //Map
    }

    /**
     * List相关
     */
    private fun listTest() {
        //不可变List，List 的默认实现是 ArrayList
        val numList = listOf("one", "two", "three")
        println(numList[0]) //one
        println(numList.get(0)) //one
        println(numList.lastIndex) //最后一个元素位置：2

        val initList = List(3) { it * it } //第一个参数是size，第二个参数是初始化函数
        println(initList) // [0, 1, 4]

        //List之间的比较
        val numList2 = listOf("two", "one", "three")
        println("numList==numList2:${numList == numList2}") //false  内容和元素都一致时才相等

        //可变List
        val variableList = mutableListOf("one", "two", "three")
        println(variableList) // 原始数据：[one, two, three]
        variableList.add("three")
        println(variableList) // 添加一条数据：[one, two, three, three]
        variableList.removeAt(0)
        println(variableList) // 删除第一条数据：[two, three, three]
        variableList.remove("three")
        println(variableList) // 删除符合条件的第一条element: [two, three]
        variableList[0] = "newOne"
        println(variableList) // 更新第一条数据：[newOne, three]
        variableList.shuffle()
        println(variableList) // 随机数据：[three, newOne]
    }

    /**
     * Set相关
     */
    private fun setTest() {
        //Set常用API Set的默认实现 - LinkedHashSet(保留元素插入的顺序)
        val numSet = setOf("one", "two", "three")
        println(numSet.first()) // one
        println(numSet.last()) // three
        for (index in numSet.indices) {
            //Set遍历 index位置
            println("index:$index")  //index:0  index:1   index:2
        }
        numSet.forEach {
            //Set遍历 Entry
            println(it) // one  two  three
        }
        numSet.forEachIndexed { index, entry ->
            //Set遍历  index & Entry
            println("index:$index, entry:$entry")
            /**
             * 执行结果：
             * index: 0, entry: one
             * index: 1, entry: two
             * index: 2, entry: three
             */
        }
        //Set之间的比较
        val numSet2 = setOf("two", "one", "three")
        println("numSet==numSet2:${numSet == numSet2}") //true  内容一致为true

        //可变Set
        val variableSet = mutableSetOf("one", "two", "three")
        variableSet.add("four")
        println(variableSet) // [one, two, three, four]
        variableSet.remove("two")
        println(variableSet) // [one, three, four]
    }

    /**
     * Map相关
     */
    private fun mapTest() {
        //Map常用API，默认实现 – LinkedHashMap：迭代 Map 时保留元素插入的顺序
        val numMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3)
        println(numMap.keys) //[key1, key2, key3]
        println(numMap.values) //[1, 2, 3]
        println(numMap.entries) //[key1=1, key2=2, key3=3]
        println(numMap["key1"]) // 1
        println("${numMap.containsValue(1)}, ${1 in numMap.values}") //true true
        println("${numMap.containsKey("key1")}, ${"key1" in numMap.keys}") //true true
        numMap.forEach { entry -> println(entry) } //Map遍历 默认有顺序 key1=1 key2=2 key3=3

        //Map之间的比较
        val num2Map = mapOf("key2" to 2, "key1" to 1, "key3" to 3)
        println("numMap==num2Map :${numMap == num2Map}") //true

        //可变Map
        val variableMap = mutableMapOf("key1" to 1, "key2" to 2)
        variableMap["key3"] = 3
        variableMap.put("key1", 111)
        println(variableMap) //{key1=111, key2=2, key3=3}
    }
}