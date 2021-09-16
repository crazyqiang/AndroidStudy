package org.ninetripods.mq.study.jetpack.viewmodel

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.KConsts
import org.ninetripods.mq.study.kotlin.ktx.id

class ViewModelActivity : BaseActivity() {
    private val mToolBar: Toolbar by id(R.id.toolbar)

    override fun setContentView() {
        Log.e(KConsts.VIEW_MODEL, "onCreate")
        setContentView(R.layout.activity_view_model)
    }

    override fun initViews() {
        initToolBar(mToolBar, "Jetpack LiveData", true)
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

    override fun onDestroy() {
        Log.e(KConsts.VIEW_MODEL, "onDestroy")
        super.onDestroy()
    }

}