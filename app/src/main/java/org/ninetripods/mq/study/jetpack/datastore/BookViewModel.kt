package org.ninetripods.mq.study.jetpack.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.BookProto
import org.ninetripods.mq.study.jetpack.datastore.sharedPreferences.BookModel

class BookViewModel(private val bookRepo: BookRepo) : ViewModel() {

    val bookSpFlow = bookRepo.bookFlow

    fun saveSpData(book: BookModel) {
        bookRepo.saveBookSp(book)
    }

    /**
     * Preferences DataStore存数据 必须在协程中进行
     */
    fun savePfData(book: BookModel) {
        viewModelScope.launch {
            bookRepo.saveBookPf(book)
        }
    }

    /**
     * Preferences DataStore取数据
     */
    val bookPfFlow = bookRepo.bookPreferencesFlow

    /**
     * Proto DataStore存数据  必须在协程中进行
     */
    fun savePtData(book: BookProto.Book) {
        viewModelScope.launch {
            bookRepo.saveBookProto(book)
        }
    }

    /**
     * Proto DataStore取数据
     */
    val bookPtFlow = bookRepo.bookProtoFlow

}

class BookRepoFactory(private val bookRepo: BookRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(bookRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}