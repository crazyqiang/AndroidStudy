package org.ninetripods.mq.study.jetpack.datastore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ninetripods.mq.study.jetpack.datastore.sharedPreferences.BookSpModel

class BookViewModel(private val bookRepo: BookRepo) : ViewModel() {

    val bookFlow = bookRepo.bookFlow

    fun saveSpData(book: BookSpModel) {
        bookRepo.saveBookSp(book)
    }

}

class BookRepoFactory(private val bookRepo: BookRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(bookRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}