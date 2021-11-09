package org.ninetripods.mq.study.jetpack.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.content.edit
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.ninetripods.mq.study.BookProto
import org.ninetripods.mq.study.jetpack.datastore.ktx.*
import org.ninetripods.mq.study.jetpack.datastore.preferences.PreferenceKeys
import org.ninetripods.mq.study.jetpack.datastore.sharedPreferences.BookModel
import org.ninetripods.mq.study.jetpack.datastore.sharedPreferences.Type
import java.io.IOException

class BookRepo private constructor(val context: Context) {

    //获取SharedPreference
    private val mBookSp =
        context.applicationContext.getSharedPreferences(BOOK_PREFERENCES_NAME, Context.MODE_PRIVATE)

    /**
     * 将SP获取的对象构建成Flow，UI层可以监听该对象变化
     */
    val bookFlow = MutableStateFlow(mBookInfo)

    /**
     * SP获取对象
     */
    private val mBookInfo: BookModel
        get() {
            val bookName: String = mBookSp.getString(KEY_BOOK_NAME, "") ?: ""
            val bookPrice: Float = mBookSp.getFloat(KEY_BOOK_PRICE, 0f)

            val typeStr = mBookSp.getString(KEY_BOOK_TYPE, Type.MATH.name)
            val bookType: Type = Type.valueOf(typeStr ?: Type.MATH.name)
            return BookModel(bookName, bookPrice, bookType)
        }

    /**
     * SP存入对象
     */
    fun saveBookSp(book: BookModel) {
        mBookSp.edit {
            putString(KEY_BOOK_NAME, book.name)
            putFloat(KEY_BOOK_PRICE, book.price)
            putString(KEY_BOOK_TYPE, book.type.name)
        }
        bookFlow.value = book
    }

    /**
     * Preferences DataStore存数据
     */
    suspend fun saveBookPf(book: BookModel) {
        context.bookDataStorePf.edit { preferences ->
            preferences[PreferenceKeys.P_KEY_BOOK_NAME] = book.name
            preferences[PreferenceKeys.P_KEY_BOOK_PRICE] = book.price
            preferences[PreferenceKeys.P_KEY_BOOK_TYPE] = book.type.name
        }
    }

    /**
     * Preferences DataStore取数据 取数据时可以对Flow数据进行一系列处理
     */
    val bookPreferencesFlow: Flow<BookModel> = context.bookDataStorePf.data.catch { exception ->
        // dataStore.data throws an IOException when an error is encountered when reading data
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        //对应的Key是 Preferences.Key<T>
        val bookName = preferences[PreferenceKeys.P_KEY_BOOK_NAME] ?: ""
        val bookPrice = preferences[PreferenceKeys.P_KEY_BOOK_PRICE] ?: 0f
        val bookType = Type.valueOf(preferences[PreferenceKeys.P_KEY_BOOK_TYPE] ?: Type.MATH.name)
        return@map BookModel(bookName, bookPrice, bookType)
    }

    /**
     * Proto DataStore存数据
     */
    suspend fun saveBookProto(book: BookProto.Book) {
        context.bookDataStorePt.updateData { book }
    }

    /**
     * Proto DataStore取数据
     */
    val bookProtoFlow: Flow<BookProto.Book> = context.bookDataStorePt.data
        .catch { exception ->
            if (exception is IOException) {
                emit(BookProto.Book.getDefaultInstance())
            } else {
                throw exception
            }
        }

    companion object {
        @SuppressLint("StaticFieldLeak")
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