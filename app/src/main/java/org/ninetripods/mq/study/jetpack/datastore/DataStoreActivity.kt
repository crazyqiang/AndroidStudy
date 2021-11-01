package org.ninetripods.mq.study.jetpack.datastore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.datastorePf
import org.ninetripods.mq.study.kotlin.ktx.log

class DataStoreActivity : BaseActivity() {

    override fun setContentView() {
        setContentView(R.layout.activity_data_store)
    }

    override fun initEvents() {

    }

    /**
     * Preferences DataStore
     */
    private suspend fun getPfDataStore() {
        //Preferences.Key<T>
        val EX_COUNTER = intPreferencesKey("ex_counter")
        val exFlow: Flow<Int> = datastorePf.data.map { preferences -> preferences[EX_COUNTER] ?: 0 }
        exFlow.collect {
            log(it.toString())
        }
    }

    private suspend fun putPfDataStore() {
        val EX_COUNTER = intPreferencesKey("ex_counter")
        datastorePf.edit { settings ->
            val curValue = settings[EX_COUNTER] ?: 0
            settings[EX_COUNTER] = curValue + 1
        }
    }
}