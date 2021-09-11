package org.ninetripods.mq.study.jetpack.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.KConsts

class ViewModelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(KConsts.VIEW_MODEL, "onCreate")
        setContentView(R.layout.activity_view_model)
    }

    override fun onDestroy() {
        Log.e(KConsts.VIEW_MODEL, "onDestroy")
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e(KConsts.VIEW_MODEL, "onSaveInstanceState0")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        Log.e(KConsts.VIEW_MODEL, "onSaveInstanceState1")
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        Log.e(KConsts.VIEW_MODEL, "onRetainCustomNonConfigurationInstance")
        return super.onRetainCustomNonConfigurationInstance()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.e(KConsts.VIEW_MODEL, "onRestoreInstanceState0")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        Log.e(KConsts.VIEW_MODEL, "onRestoreInstanceState1")
        super.onRestoreInstanceState(savedInstanceState, persistentState)
    }

}