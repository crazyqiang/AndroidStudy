package org.ninetripods.mq.study.collection

data class BookModel(var id: Int, var name: String)

/**
 * Version比较，默认先比较major 再比较minor 从小到大正向排序
 */
data class Version(val major: Int, val minor: Int) : Comparable<Version> {

    /**
     * 返回值：
     * 1、正值表明它大于参数。
     * 2、负值表明它小于参数。
     * 3、返回0说明对象相等。
     */
    override fun compareTo(other: Version): Int {
        return when {
            this.major != other.major -> {
                this.major - other.major
            }
            this.minor != other.minor -> {
                this.minor - other.minor
            }
            else -> 0
        }
    }
}