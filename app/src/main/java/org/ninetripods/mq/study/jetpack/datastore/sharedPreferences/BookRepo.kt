package org.ninetripods.mq.study.jetpack.datastore.sharedPreferences

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import org.ninetripods.mq.study.jetpack.datastore.BOOK_PREFERENCES_NAME
import org.ninetripods.mq.study.jetpack.datastore.KEY_BOOK_NAME_SP

class BookRepo(context: Context) {

    private val bookSharedPreferences =
        context.applicationContext.getSharedPreferences(BOOK_PREFERENCES_NAME, Context.MODE_PRIVATE)

    val bookFlow = MutableStateFlow(mBookInfo)

    private val mBookInfo: BookSpModel
        get() {
            val bookName: String = bookSharedPreferences.getString(KEY_BOOK_NAME_SP, "") ?: ""
            return BookSpModel(bookName)
        }

    fun updateBookInfo(book: BookSpModel) {
        bookSharedPreferences.edit {
            putString(KEY_BOOK_NAME_SP, book.name)
        }
        bookFlow.value = book
    }


}