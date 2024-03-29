package org.ninetripods.mq.study.jetpack.datastore.sharedPreferences

/**
 * SharedPreferences
 */
data class BookModel(
    var name: String = "",
    var price: Float = 0f,
    var type: Type = Type.ENGLISH
)

enum class Type {
    MATH,
    CHINESE,
    ENGLISH
}