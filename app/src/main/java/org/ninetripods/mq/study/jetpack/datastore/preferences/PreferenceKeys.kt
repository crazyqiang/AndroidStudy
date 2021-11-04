package org.ninetripods.mq.study.jetpack.datastore.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * 用来声明Preferences.Key<T>
 */
object PreferenceKeys {
    val KEY_STRING = stringPreferencesKey("key_string")
    val KEY_INT = intPreferencesKey("key_int")
    val KEY_BOOLEAN = booleanPreferencesKey("key_boolean")
}