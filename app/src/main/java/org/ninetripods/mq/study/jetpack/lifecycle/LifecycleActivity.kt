package org.ninetripods.mq.study.jetpack.lifecycle

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.KConsts
import org.ninetripods.mq.study.jetpack.KConsts.ACTIVITY
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.util.NavitateUtil

class LifecycleActivity : BaseActivity() {

    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val mTvResult: TextView by id(R.id.tv_result)

    private val mBuilder = StringBuilder()
    private var mServiceAlive = false

    //自定义LifecycleOwner 如果想让一个自定义类成为LifecycleOwner，可以直接实现LifecycleOwner
    private val customOwner: MyLifecycleOwner = MyLifecycleOwner()

    override fun setContentView() {
        setContentView(R.layout.activity_jetpack_lifecycle_layout)
    }

    override fun initViews() {
        initToolBar(mToolBar, "Jetpack Lifecycle", true)
    }

    override fun initEvents() {
        Log.e(KConsts.LIFE_TAG, "$ACTIVITY:onCreate")

        //1、添加生命周期观察者
        //lifecycle.addObserver(MyLifeCycleObserver())

        //2、自定义LifecycleOwner
        customOwner.init()
        //添加生命周期观察者 在控制台查看日志
        customOwner.lifecycle.addObserver(MyLifeCycleObserver())
    }

    override fun onStart() {
        Log.e(KConsts.LIFE_TAG, "$ACTIVITY:onStart")
        super.onStart()
        customOwner.onStart()
    }

    override fun onResume() {
        Log.e(KConsts.LIFE_TAG, "$ACTIVITY:onResume")
        super.onResume()
        customOwner.onResume()
    }

    override fun onPause() {
        Log.e(KConsts.LIFE_TAG, "$ACTIVITY:onPause")
        super.onPause()
        customOwner.onPause()
    }

    override fun onStop() {
        Log.e(KConsts.LIFE_TAG, "$ACTIVITY:onStop")
        super.onStop()
        customOwner.onStop()
    }

    override fun onDestroy() {
        Log.e(KConsts.LIFE_TAG, "$ACTIVITY:onDestroy")
        super.onDestroy()
        customOwner.onDestroy()
    }

    /**
     * 启动Service
     */
    fun startLifecycleService(view: View) {
        val intent = Intent(this, MyLifecycleService::class.java)
        startService(intent)
        getStartLogStr()
    }

    /**
     * 关闭Service
     */
    fun closeService(view: View) {
        val intent = Intent(this, MyLifecycleService::class.java)
        stopService(intent)
        if (mServiceAlive) {
            getCloseLogStr()
        }
    }

    /**
     * 跳转到系统Home页面 用于展示应用前后台判断 {@link MyApplicationLifecycleObserver}
     */
    fun clickGoHome(view: View) {
        NavitateUtil.goHome(this)
    }

    /**
     * 模拟将日志输出到界面上
     */
    private fun getStartLogStr() {
        mServiceAlive = true
        mBuilder.clear()
        mBuilder.append("Service:onCreate").append("\n")
        mBuilder.append("Lifecycle.Event:ON_CREATE").append("\n").append("\n")
        mBuilder.append("Service:onStart").append("\n")
        mBuilder.append("Lifecycle.Event:ON_START").append("\n").append("\n")
        mTvResult.text = mBuilder.toString()
    }

    private fun getCloseLogStr() {
        mServiceAlive = false
        mBuilder.append("Service:onDestroy").append("\n")
        mBuilder.append("Lifecycle.Event:ON_DESTROY").append("\n")
        mTvResult.text = mBuilder.toString()
    }

}