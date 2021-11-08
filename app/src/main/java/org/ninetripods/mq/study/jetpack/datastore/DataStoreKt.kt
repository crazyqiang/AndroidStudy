package org.ninetripods.mq.study.jetpack.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.ninetripods.mq.study.BookProto
import org.ninetripods.mq.study.jetpack.datastore.proto.BookSerializer

//SharedPreference
const val BOOK_PREFERENCES_NAME = "book_preferences"
const val KEY_BOOK_NAME = "key_book_name"
const val KEY_BOOK_PRICE= "key_book_price"
const val KEY_BOOK_TYPE= "key_book_type"

//构建Preferences DataStore
val Context.bookDataStorePf: DataStore<Preferences> by preferencesDataStore(
    name = "ds_preference",
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, BOOK_PREFERENCES_NAME))
    }
)

//构建Proto DataStore
val Context.bookDataStorePt: DataStore<BookProto.Book> by dataStore(
    fileName = "BookSpModel.pb",
    serializer = BookSerializer
)