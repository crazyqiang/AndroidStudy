package org.ninetripods.mq.study.kotlin.ktx

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.ninetripods.mq.study.BookProto
import org.ninetripods.mq.study.jetpack.datastore.proto.BookSerializer

//构建Preferences DataStore
val Context.bookDataStorePf: DataStore<Preferences> by preferencesDataStore(
    name = "ds_preference"
)

//构建Proto DataStore
val Context.bookDataStorePt: DataStore<BookProto.Book> by dataStore(
    fileName = "Book.pb",
    serializer = BookSerializer()
)