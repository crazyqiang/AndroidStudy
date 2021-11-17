package org.ninetripods.mq.study.jetpack.datastore.ktx

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.migrations.SharedPreferencesView
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.ninetripods.mq.study.BookProto
import org.ninetripods.mq.study.jetpack.datastore.proto.BookSerializer

//SharedPreference
const val BOOK_PREFERENCES_NAME = "book_preferences"
const val KEY_BOOK_NAME = "key_book_name"
const val KEY_BOOK_PRICE = "key_book_price"
const val KEY_BOOK_TYPE = "key_book_type"

//构建Preferences DataStore
val Context.bookDataStorePf: DataStore<Preferences> by preferencesDataStore(
    name = "pf_datastore",
    //将SP迁移到Preference DataStore中
    produceMigrations = { context ->
        listOf(SharedPreferencesMigration(context, BOOK_PREFERENCES_NAME))
    }
)

//构建Proto DataStore
val Context.bookDataStorePt: DataStore<BookProto.Book> by dataStore(
    fileName = "BookProto.pb",
    serializer = BookSerializer,
    //将SP迁移到Proto DataStore中
    produceMigrations = { context ->
        listOf(
            androidx.datastore.migrations.SharedPreferencesMigration(
                context,
                BOOK_PREFERENCES_NAME
            ) { sharedPrefs: SharedPreferencesView, currentData: BookProto.Book ->
                //从SP中取出数据
                val bookName: String = sharedPrefs.getString(KEY_BOOK_NAME, "") ?: ""
                val bookPrice: Float = sharedPrefs.getFloat(KEY_BOOK_PRICE, 0f)

                val typeStr = sharedPrefs.getString(KEY_BOOK_TYPE, BookProto.Type.MATH.name)
                val bookType: BookProto.Type =
                    BookProto.Type.valueOf(typeStr ?: BookProto.Type.MATH.name)

                //将SP中的数据存入Proto DataStore中
                currentData.toBuilder()
                    .setName(bookName)
                    .setPrice(bookPrice)
                    .setType(bookType)
                    .build()
            }
        )
    }
)