package org.ninetripods.mq.study.jetpack.datastore

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import org.ninetripods.mq.study.jetpack.datastore.sharedPreferences.BookSpModel
import org.ninetripods.mq.study.jetpack.datastore.sharedPreferences.Type

class BookRepo private constructor(context: Context) {

    //获取SharedPreference
    private val mBookSp =
        context.applicationContext.getSharedPreferences(BOOK_PREFERENCES_NAME, Context.MODE_PRIVATE)

    /**
     * 将对象构建成Flow，UI层可以监听该对象变化
     */
    val bookFlow = MutableStateFlow(mBookInfo)

    /**
     * SP获取对象
     */
    private val mBookInfo: BookSpModel
        get() {
            val bookName: String = mBookSp.getString(KEY_BOOK_NAME, "") ?: ""
            val bookPrice: Float = mBookSp.getFloat(KEY_BOOK_PRICE, 0f)

            val typeStr = mBookSp.getString(KEY_BOOK_TYPE, Type.MATH.name)
            val bookType: Type = Type.valueOf(typeStr ?: Type.MATH.name)
            return BookSpModel(bookName, bookPrice, bookType)
        }

    /**
     * SP存入对象
     */
    fun saveBookSp(book: BookSpModel) {
        mBookSp.edit {
            putString(KEY_BOOK_NAME, book.name)
            putFloat(KEY_BOOK_PRICE, book.price)
            putString(KEY_BOOK_TYPE, book.type.name)
        }
        bookFlow.value = book
    }

    companion object {
        @Volatile
        private var INSTANCE: BookRepo? = null

        fun getInstance(context: Context): BookRepo {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let { return it }
                val instance = BookRepo(context)
                INSTANCE = instance
                return instance
            }
        }
    }


}