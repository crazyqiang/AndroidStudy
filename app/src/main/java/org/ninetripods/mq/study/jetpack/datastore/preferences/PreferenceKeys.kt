package org.ninetripods.mq.study.jetpack.datastore.preferences

import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import org.ninetripods.mq.study.jetpack.datastore.KEY_BOOK_NAME
import org.ninetripods.mq.study.jetpack.datastore.KEY_BOOK_PRICE
import org.ninetripods.mq.study.jetpack.datastore.KEY_BOOK_TYPE

/**
 * 用来声明Preferences.Key<T>
 */
object PreferenceKeys {
    val P_KEY_BOOK_NAME = stringPreferencesKey(KEY_BOOK_NAME)
    val P_KEY_BOOK_PRICE = floatPreferencesKey(KEY_BOOK_PRICE)
    val P_KEY_BOOK_TYPE = stringPreferencesKey(KEY_BOOK_TYPE)
}