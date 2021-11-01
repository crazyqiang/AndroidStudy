package org.ninetripods.mq.study.kotlin.ktx

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.datastorePf: DataStore<Preferences> by preferencesDataStore(name = "ds_preference")