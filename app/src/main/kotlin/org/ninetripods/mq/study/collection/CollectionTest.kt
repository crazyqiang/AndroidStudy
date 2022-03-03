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
        //取List一部分
        println(numList.subList(0, 2))//左边右开区间，如果越界会抛异常。执行结果：[one, two]

        //first()
        println(numList.first()) //one 取第一个元素
        println(numList.first { it.length > 3 }) //按条件取满足条件的第一个元素 都没有的话抛异常 执行结果：three
        //find() 等同于 firstOrNull()
        println(numList.firstOrNull { it.length > 5 }) //null
        println(numList.find { it.length > 5 }) //null

        //last()
        println(numList.last()) //three 取最后一个元素
        println(numList.last { it.contains("o") }) //two
        //findLast() = lastOrNull()
        println(numList.lastOrNull { it.length > 5 }) //null
        println(numList.findLast { it.length > 5 }) //null

        //index为3的位置没有元素
        println(numList.elementAtOrNull(3)) //null
        println(numList.elementAtOrElse(3) { index ->
            "The value for index $index is undefined" //The value for index 3 is undefined
        })

        println(numList.random()) //随机取一个元素

        println(numList.isEmpty())//false
        println(numList.isNotEmpty()) //true
        println(numList.isNullOrEmpty()) //false

        val initList = List(3) { it * it } //第一个参数是size，第二个参数是初始化函数
        println(initList) // [0, 1, 4]

        //List之间的比较
        val numList2 = listOf("two", "one", "three")
        println("numList==numList2:${numList == numList2}") //false  内容和元素都一致时才相等

        //可变List
        val origins = mutableListOf("one", "two", "three")
        println(origins) // 原始数据：[one, two, three]
        origins.add("three")
        println(origins) // 添加一条数据：[one, two, three, three]
        origins.removeAt(0)
        println(origins) // 删除第一条数据：[two, three, three]
        origins.remove("three")
        println(origins) // 删除符合条件的第一条element: [two, three]
        origins[0] = "newOne"
        println(origins) // 更新第一条数据：[newOne, three]
        origins.shuffle()
        println(origins) // 随机数据：[three, newOne]
        origins.removeAll { it.length == 3 }
        println(origins) //删除全部符合条件的元素 [three, newOne]
        println(origins.retainAll { it.length == 3 }) //保留全部符合条件的元素

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

        //union()并集、intersect()交集、subtract()差集
        val numSets = setOf("one", "two", "three")
        println(numSets union setOf("four", "five")) //[one, two, three, four, five]
        println(numSets intersect setOf("two", "one")) //[one, two]
        println(numSets subtract setOf("three", "four")) //[one, two]
    }

    /**
     * Map相关
     */
    private fun mapTest() {
        //Map常用API，默认实现 – LinkedHashMap：迭代 Map 时保留元素插入的顺序
        val numMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3)
        println(numMap.keys) //[key1, key2, key3]
        println(numMap.values) //[1, 2, 3]  注意：在.values中调用remove()仅删除给定值匹配到的的第一个条目。
        println(numMap.entries) //[key1=1, key2=2, key3=3]
        println(numMap["key1"]) // 1
        //println(numMap.getOrDefault("key4", 4)) //API24添加 执行结果：4
        println("${numMap.containsValue(1)}, ${1 in numMap.values}") //true true
        println("${numMap.containsKey("key1")}, ${"key1" in numMap.keys}") //true true
        numMap.forEach { entry -> println(entry) } //Map遍历 默认有顺序 key1=1 key2=2 key3=3

        //filter()过滤操作
        val filterMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
        println(filterMap.filter { (key, value) ->
            key.endsWith("1") && value < 10  //{key1=1}
        })
        //filterKeys()过滤key
        println(filterMap.filterKeys { it.endsWith("2") }) //{key2=2}
        //filterValues()过滤value
        println(filterMap.filterValues { it < 3 })  //{key1=1, key2=2}

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
        transformCollection() //转换集合
        traverseCollection() //遍历集合
        plusMinusCollection()//加减集合
        groupByCollection()//集合分组
        partCollection() //集合的一部分
        orderCollection() //集合排序
        polymerCollection() //集合聚合

        //filter()不会影响原始集合数据，而是产生一个新的集合
        val numbers = mutableListOf("one", "two", "three", "four")
        val filterNums = numbers.filter { it.length > 3 }
        println("numbers: $numbers") //numbers: [one, two, three, four]
        println("filterNums:$filterNums")  //filterNums:[three, four]

        //To相关操作符
        val filterResults = mutableListOf("1", "2")
        numbers.filterTo(filterResults, { it.length > 3 })
        println("numbers: $numbers") //numbers: [one, two, three, four]
        println("filterResults:$filterResults")  //filterResults:[1, 2, three, four]
        //可以看到`filterTo`并没有创建新集合，而是将结果添加到已知的集合中去了。

        //写操作
        //对于可变集合，还存在可更改集合状态的`写操作` 。这些操作包括`添加、删除和更新元素`。
        //对于某些操作，有成对的函数可以执行相同的操作：`一个函数就地应用该操作，另一个函数将结果作为单独的集合返回`
        val sortedNums = numbers.sorted()
        println("numbers: $numbers") //numbers: [one, two, three, four]
        println("sortedNums:$sortedNums") //numbers: [one, two, three, four]  所以sorted()没有改变原始集合
        numbers.sort()
        println("numbers: $numbers") //numbers: [one, two, three, four]  所以sort()直接在原始集合上进行改动
    }

    /**
     * copy集合 创建与现有集合具有相同元素的集合，可以使用复制操作，例如`toList()、toMutableList()、toSet()` 等等。
     * 标准库中的集合复制操作创建了具有相同元素引用的 `浅复制`集合。 因此，`对集合元素所做的更改会反映在其所有副本中，
     * 如果对源集合进行添加或删除元素，则不会影响副本`。
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
     * 集合转换map()、zip()、associate()、flatten()、flatMap()
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
         * ----------------associate()关联----------------
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
     * 集合加减
     */
    private fun plusMinusCollection() {
        val numbers = listOf("one", "two", "three", "three")
        val plusNumbers = numbers + "four"
        println(numbers) //原始集合：[one, two, three, three]
        println(plusNumbers) //集合加操作：[one, two, three, three, four]

        val minusNum1 = numbers - listOf("three")
        val minusNum2 = numbers - "three"
        println(minusNum1)//集合减操作1：[one, two]
        println(minusNum2) //集合减操作2：[one, two, three]
        //注意：minus操作，如果第二个操作数是一个元素，那么 minus 移除其在原始集合中的 第一次 出现；
        // 如果是一个集合，那么移除其元素在原始集合中的 所有 出现。
    }

    /**
     * groupBy()、groupingBy()集合分组
     */
    private fun groupByCollection() {
        val numbers = listOf("one", "two", "three")
        //groupBy() 使用一个 lambda 函数并返回一个 Map。 在此 Map 中，每个键都是 lambda 结果，而对应的值是返回此结果的元素 List。
        //在带有两个 lambda 的 groupBy() 结果 Map 中，由 keySelector 函数生成的键映射到值转换函数的结果，而不是原始元素。
        println(numbers.groupBy { it.first() })//{o=[one], t=[two, three]}
        println(
            numbers.groupBy(keySelector = { it.length }, valueTransform = { it })
            //{3=[one, two], 5=[three]}
        )
        //https://www.kotlincn.net/docs/reference/collection-grouping.html
        println(numbers.groupingBy { it.first() }.eachCount()) //{o=1, t=2, f=2, s=1}
    }

    /**
     * slice()、take()、drop()、chunked()、windowed()、zipWithNext()取集合的一部分
     */
    private fun partCollection() {
        val numbers = listOf("one", "two", "three", "four")

        //slice()
        println(numbers.slice(1..2)) //slice(indices: IntRange) 执行结果：[two, three]
        println(numbers.slice(0..3 step 2)) //slice(indices: IntRange)间隔2，执行结果：[one, three]
        println(numbers.slice(listOf(1, 2))) //slice(indices: Iterable<Int>) 执行结果：[two, three]

        //take() & drop()
        //take:从头开始获取指定数量的元素; takeLast:从尾开始获取指定数量的元素
        println(numbers.take(2)) //[one, two]
        println(numbers.takeLast(2)) //[three, four]
        println(numbers.drop(1)) //[two, three, four]
        println(numbers.dropLast(2))//[one, two]

        //takeWhile() & dropWhile()
        //takeWhile()不停获取元素直到排除与谓词匹配的首个元素。如果首个集合元素与谓词匹配，则结果为空。
        println(numbers.takeWhile { !it.startsWith("f") })//[one, two, three]
        println(numbers.takeLastWhile { !it.startsWith("t") })//[four]
        //dropWhile()将首个与谓词不匹配的元素返回到末尾
        println(numbers.dropWhile { it.length == 3 }) //[three, four]
        println(numbers.dropLastWhile { it.length > 3 })//[one, two]

        //chunk():要将集合分解为给定大小的“块”,最后一个块的大小可能较小
        val nums = (0..7).toList()
        println(nums) //[0, 1, 2, 3, 4, 5, 6, 7]
        println(nums.chunked(3)) //List<List<T>>类型： [[0, 1, 2], [3, 4, 5], [6, 7]]
        //还可以对返回的块进行转换，如下：
        println(nums.chunked(3) { it.sum() }) //[3, 12, 13]

        val numWindows = (0..7).toList()
        println(numWindows.windowed(3)) //[[0, 1, 2], [1, 2, 3], [2, 3, 4], [3, 4, 5], [4, 5, 6], [5, 6, 7]]
        //step:定义相邻两个窗口第一个元素之间的距离 partialWindows：是否包含最后较少元素，true包含 false不包含
        println(numWindows.windowed(3, step = 2, partialWindows = true))
        //执行结果：[[0, 1, 2], [2, 3, 4], [4, 5, 6], [6, 7]]
        println(numWindows.windowed(3) { it.sum() }) //[3, 6, 9, 12, 15, 18]

        //zipWithNext()单独创建两个元素的窗口
        println(numWindows.zipWithNext()) //List<Pair<T, T>>执行结果：[(0, 1), (1, 2), (2, 3), (3, 4), (4, 5), (5, 6), (6, 7)]
        println(numWindows.zipWithNext { s1, s2 -> s1 * s2 }) //List<R>执行结果：[0, 2, 6, 12, 20, 30, 42]
    }

    /**
     * 集合排序 Comparable可以理解为内部比较，Comparator为外部的比较
     */
    private fun orderCollection() {
        //Comparable进行比较
        println(Version(1, 2) > Version(1, 3)) //false
        println(Version(2, 0) > Version(1, 5)) //true

        val origins = listOf("aaa", "c", "bb")
        //sortedWith() + Comparator自定义顺序进行比较
        val lengthComparator = Comparator { o1: String, o2: String -> o1.length - o2.length }
        println(origins.sortedWith(lengthComparator)) //[c, bb, aaa]
        println(origins) //[aaa, bb, c]
        //继续简化
        println(origins.sortedWith(compareBy { it.length })) //[c, bb, aaa]
        //上述sortedWith(compareBy{})代码简写为sortedBy{}
        println(origins.sortedBy { it.length }) //[c, bb, aaa]
        println(origins.sortedByDescending { it.length }) //[aaa, bb, c]

        //自然顺序
        println(origins.sorted()) //[aaa, bb, c]
        println(origins.sortedDescending()) //[c, bb, aaa]

        //倒序 reversed()产生新集合，改变原始集合不会影响新集合；
        val originStrs = mutableListOf("aaa", "c", "bb")
        println(originStrs.reversed()) //使用reversed()倒序 执行结果：[bb, c, aaa]
        //使用asReversed()倒序
        val asReverseList = originStrs.asReversed()
        println(asReverseList) //[bb, c, aaa]
        originStrs.add("dd") //对原始集合进行改动
        println(asReverseList) //原始集合变化，倒序的集合也自动更新了 执行结果：[dd, bb, c, aaa]

        //随机顺序
        val shuffledNums = listOf("one", "two", "three")
        println(shuffledNums.shuffled())//随机产生一个新的集合
        println(shuffledNums) //[one, two, three]
    }

    /**
     * 集合聚合 maxOrNull()、minOrNull()、average()、sum()、sumBy()、reduce()、fold()
     */
    private fun polymerCollection() {
        val numbers = listOf(30, 20, 40, 10)
        println(numbers.count()) //元素数量 4
        println(numbers.maxOrNull()) //最大元素 40
        println(numbers.minOrNull()) //最小元素 10
        println(numbers.average()) //平均值 25.0
        println(numbers.sum())//集合元素的总和 100

        //接受一个选择器函数并返回使选择器返回最大或最小值的元素。
        val min3Remainder = numbers.minByOrNull { it % 3 } //30
        val max3Remainder = numbers.maxByOrNull { it % 3 } //20
        //接受一个 Comparator 对象并且根据此 Comparator 对象返回最大或最小元素
        val maxNum = numbers.maxWithOrNull(compareBy { it }) //40
        val minNum = numbers.minWithOrNull(compareBy { it }) //10
        println(min3Remainder) //30
        println(max3Remainder) //20
        println(maxNum) //40
        println(minNum) //10

        println(numbers.sumBy { it * 2 }) //对lambda函数返回的Int结果进行求和 200
        println(numbers.sumByDouble { it.toDouble() / 2 }) //对lambda函数返回的Double结果进行求和 50.0

        //fold() & reduce() 依次将所提供的操作应用于集合元素并返回累积的结果。
        //区别：fold() 接受一个初始值并将其用作第一步的累积值，而 reduce() 的第一步则将第一个和第二个元素作为第一步的操作参数。
        val sum = numbers.reduce { sum, element -> sum + element }
        println(sum) //100

        //val numbers = listOf(30, 20, 40, 10)
        //reduce()
        val sumDouble = numbers.reduce { sum1, element ->
            print("$sum1 ") //30 70 150 170
            sum1 + element * 2
        }
        println(sumDouble) //170
        //reduceRight() 操作参数会更改其顺序：第一个参数变为元素，然后第二个参数变为累积值。
        val sumDoubleRight = numbers.reduceRight { element, sum2 ->
            print("$sum2 ") //10 90 130 190
            sum2 + element * 2
        }
        println(sumDoubleRight) //190
        //reduceIndexed()
        val sumIndex = numbers.reduceIndexed { index, sumI, element ->
            print("index:$index,sum:$sumI,element:$element ")
            /**
             * index:1,sum:30,element:20
             * index:2,sum:50,element:40
             * index:3,sum:90,element:10
             */
            sumI + element
        }
        println(sumIndex)
        //reduceRightIndexed()
        val sumIndexRight = numbers.reduceRightIndexed { index, sumR, element ->
            print("index:$index,sum:$sumR,element:$element ")
            /**
             * index:2,sum:40,element:10
             * index:1,sum:20,element:50
             * index:0,sum:30,element:70
             */
            sumR + element
        }
        println(sumIndexRight) //100

        //注：为了防止为null时抛异常，可以使用对应的reduceOrNull()、
        // reduceRightOrNull()、reduceIndexedOrNull()、reduceRightIndexedOrNull()

        //val numbers = listOf(30, 20, 40, 10)
        //fold()
        val sumDouble1 = numbers.fold(0) { sum3, element ->
            print("$sum3 ") //0 60 100 180 200
            sum3 + element * 2
        }
        println(sumDouble1) //200
        //foldRight() 操作参数会更改其顺序：第一个参数变为元素，然后第二个参数变为累积值。
        val sumDoubleRight1 = numbers.foldRight(0) { element, sum4 ->
            print("$sum4 ") //0 20 100 140 200
            sum4 + element * 2
        }
        println(sumDoubleRight1) //200
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