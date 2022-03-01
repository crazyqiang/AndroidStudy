package org.ninetripods.mq.study.collection

import java.util.*

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
        processCollection() //集合操作
        processSequence() //序列相关 Sequence vs Iterable
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

        //List转为Map
        val numbers = listOf("one", "two", "three", "four")
        println(numbers.associateWith { it.length }) // {one=3, two=3, three=5, four=4}
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

    /**
     * 集合操作
     */
    private fun processCollection() {
        copyCollection() //copy集合相关操作
        traverseCollection() //遍历集合

        transformCollection() //转换集合
        //不会影响原始集合数据，而是产生一个新的集合
        val numbers = mutableListOf("one", "two", "three", "four")
        val filterNums = numbers.filter { it.length > 3 }
        println("numbers: $numbers") //numbers: [one, two, three, four]
        println("filterNums:$filterNums")  //filterNums:[three, four]

        //To相关操作符
        val filterResults = mutableListOf("1", "2")
        numbers.filterTo(filterResults, { it.length > 3 })
        println("numbers: $numbers") //numbers: [one, two, three, four]
        println("filterResults:$filterResults")  //filterResults:[1, 2, three, four]

        //写操作
        val sortedNums = numbers.sorted()
        println("numbers: $numbers") //numbers: [one, two, three, four]
        println("sortedNums:$sortedNums") //numbers: [one, two, three, four]  所以sorted()没有改变原始集合
        numbers.sort()
        println("numbers: $numbers") //numbers: [one, two, three, four]  所以sort()直接在原始集合上进行改动
    }

    /**
     * copy集合
     */
    private fun copyCollection() {
        val sourceList = mutableListOf<BookModel>()
        for (i in 0..1) {
            sourceList.add(BookModel(i, "Android"))
        }
        println("sourceList:$sourceList")
        val copyList = sourceList.toMutableList()
        println("sourceList新增数据:")
        sourceList.add(BookModel(100, "IOS"))
        println("sourceList:$sourceList")
        println("copyList:$copyList")
        sourceList.forEachIndexed { index, bookModel ->
            bookModel.name = "Android$index"
        }
        println("sourceList修改之后:")
        println("sourceList:$sourceList")
        println("copyList:$copyList")
        //执行结果：
        // sourceList:[BookModel(id=0, name=Android), BookModel(id=1, name=Android)]
        // sourceList新增数据:
        // sourceList:[BookModel(id=0, name=Android), BookModel(id=1, name=Android), BookModel(id=100, name=IOS)]
        // copyList:[BookModel(id=0, name=Android), BookModel(id=1, name=Android)]
        // sourceList修改之后:
        // sourceList:[BookModel(id=0, name=Android0), BookModel(id=1, name=Android1), BookModel(id=100, name=Android2)]
        // copyList:[BookModel(id=0, name=Android0), BookModel(id=1, name=Android1)]
    }

    /**
     * 遍历集合
     */
    private fun traverseCollection() {
        //正向迭代
        //1
        val numbers = mutableListOf("one", "two", "three", "four")
        for (pos in 0..3) {//表示0<=pos && pos<=3,即pos在[0,3]内;或者用numbers.indices
            print("${numbers[pos]} ") // one two three four
        }
        //如果是左闭右开区间[0,4)，使用until关键字
        for (pos in 0 until 4) {
            print("${numbers[pos]} ") // one two three four
        }
        //2
        numbers.forEach { print("$it ") } //one two three four
        //3
        numbers.forEachIndexed { index, number -> println("index:$index, number:$number") }
        /**
         * index:0, number:one
         * index:1, number:two
         * index:2, number:three
         * index:3, number:four
         */

        //反向迭代
        for (pos in 3 downTo 0) {
            print("${numbers[pos]} ") //four three two one
        }

        //任意步长迭代, 如下面的步长为2
        for (pos in 0..3 step 2) {
            print("${numbers[pos]} ") //one three
        }

    }

    /**
     * 集合转换
     */
    private fun transformCollection() {
        val numbers = listOf("one", "two", "three")
        val mIndexes = listOf(1, 2, 3)
        val mTwoIndex = listOf(1, 2)
        /**
         * ----------------map()映射----------------
         */
        //map()、mapNotNull()映射函数，区别是mapNotNull()会过滤掉结果为null的值
        println(numbers.map { "it's $it" }) //[it's one, it's two, it's three]
        println(numbers.mapNotNull { if (it.length == 3) null else it }) //[three]

        //mapIndexed()、mapIndexedNotNull()带有元素索引位置的映射函数，区别是mapIndexedNotNull()会过滤掉结果为null的值
        println(numbers.mapIndexed { index, s -> "$index-$s" }) //[0-one, 1-two, 2-three]
        println(numbers.mapIndexedNotNull { index, s ->
            if (s.length == 3) null else "$index-$s" //[2-three]
        })

        //mapKeys() & mapValues()
        val numMap = mapOf("one" to 1, "two" to 2, "three" to 3)
        println(numMap) //{one=1, two=2, three=3}
        println(numMap.mapKeys { it.key.toUpperCase(Locale.ROOT) }) //{ONE=1, TWO=2, THREE=3}
        println(numMap.mapValues { it.value + it.key.length }) //{one=4, two=5, three=8}

        /**
         * ----------------zip()合拢----------------
         */
        //zip()操作。如果集合的大小不同，则 zip() 的结果为较小集合的大小
        println(numbers.zip(mIndexes)) //[(one, 1), (two, 2), (three, 3)]
        println(numbers zip mTwoIndex) //中缀表达式方式  [(one, 1), (two, 2)]

        //zip()中第2个参数为转换函数的使用举例
        println(numbers.zip(mIndexes) { number, index -> "number:$number index:$index" })
        //执行结果：[number:one index:1, number:two index:2, number:three index:3]

        //unzip()函数
        val numPairs: List<Pair<String, Int>> = listOf("one" to 1, "two" to 2, "three" to 3)
        println(numPairs.unzip()) //([one, two, three], [1, 2, 3])
        println(numPairs.unzip().first) //[one, two, three]
        println(numPairs.unzip().second) //[1, 2, 3]

        /**
         * ----------------associate关联----------------
         */
        //List转为Map，所以当key相同时，value会被最新的覆盖
        println(numbers.associateWith { it.length }) //{one=3, two=3, three=5}
        //associateBy将元素作为value来构建Map
        println(numbers.associateBy { it.first() }) //{o=one, t=three}
        println(numbers.associateBy(
            //自行设计key和value
            keySelector = { it.first() }, valueTransform = { it.length }) //{o=3, t=5}
        )
        println(numbers.associate { it.first() to it.length }) // {o=3, t=5}

        /**
         * ----------------flatten()、flatMap()----------------
         * flatten()返回嵌套集合集合中的所有元素的List
         * flatMap()需要一个函数将一个集合元素映射到另一个集合。返回单个列表其中包含所有元素的值。等于map()+flatten()的连续调用
         */
        val containers = listOf(
            listOf("one", "two"),
            listOf("three", "four", "five")
        )
        println(containers.flatten()) //[one, two, three, four, five]
        println(containers.flatMap { subs ->
            listOf(subs)  //[2, 3]
        })

    }

    /**
     * Sequence序列相关
     */
    private fun processSequence() {
        //创建Sequence
        val sequenceNum = sequenceOf("one", "two", "three", "four")
        println("sequenceNum: $sequenceNum")

        //Iterable转为Sequence
        val numbers = listOf("one", "two", "three", "four")
        val numSequence = numbers.asSequence()
        println("numSequence: $numSequence")

        //通过函数generateSequence()创建序列，默认创建的序列是无限的；如果想创建有限数列，那么最后一个元素需要返回null
        val oddNumbers = generateSequence(1) { it + 2 } // `it` 是上一个元素
        println(oddNumbers.take(5).toList()) // [1, 3, 5, 7, 9]

        //sequence()函数可以将组块生成序列，yield()-生产单个元素、yieldAll()-可以生产多个、无限个元素
        val oNumbers = sequence {
            yield(1)
            yieldAll(listOf(3, 5))
            yieldAll(generateSequence(7) { it + 2 })
        }
        println(oNumbers.take(4).toList()) // [1, 3, 5, 7]

        //Iterable & Sequence执行顺序  举例：过滤长于三个字符的单词，并打印前四个单词的长度。
        //Iterable
        val words = "The quick brown fox jumps over the lazy dog".split(" ")
        val lengthsList = words.filter { println("filter: $it"); it.length > 3 }
            .map { println("length: ${it.length}"); it.length }
            .take(4)

        println("Lengths of first 4 words longer than 3 chars:")
        println(lengthsList)

        //Sequence
        val originWords = "The quick brown fox jumps over the lazy dog".split(" ")
        // 将列表转换为序列
        val wordsSequence = originWords.asSequence()

        val lengthsSequence = wordsSequence.filter { println("filter: $it"); it.length > 3 }
            .map { println("length: ${it.length}"); it.length }
            .take(4)

        println("Lengths of first 4 words longer than 3 chars")
        // 末端操作：以列表形式获取结果。
        println(lengthsSequence.toList())
    }
}