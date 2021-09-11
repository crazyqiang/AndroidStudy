package org.ninetripods.mq.study.jetpack.lifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.KConsts
import org.ninetripods.mq.study.jetpack.KConsts.ACTIVITY

class LifecycleActivity : AppCompatActivity() {

    private lateinit var mText: TextView
    private lateinit var model: NameViewModel
    val owner: CustomLifeCycleOwner = CustomLifeCycleOwner()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack_lifecycle_layout)
        Log.e(KConsts.LIFE_TAG, "$ACTIVITY:onCreate")

        //1、添加生命周期观察者
        //lifecycle.addObserver(MyLifeCycleObserver())

        //2、自定义LifecycleOwner
        owner.init()
        //添加生命周期观察者
        //owner.lifecycle.addObserver(MyLifeCycleObserver())

        //3、启动Service
        startLifecycleService()

        model = ViewModelProvider(this).get(NameViewModel::class.java)

        mText = findViewById(R.id.tv_text)

        val nameObserver = Observer<User> { user ->
            mText.text = user.name
        }
        model.nameLiveData.observe(this, nameObserver)
    }

    override fun onStart() {
        Log.e(KConsts.LIFE_TAG, "$ACTIVITY:onStart")
        super.onStart()
        owner.onStart()
    }

    override fun onResume() {
        Log.e(KConsts.LIFE_TAG, "$ACTIVITY:onResume")
        super.onResume()
        owner.onResume()
    }

    override fun onPause() {
        Log.e(KConsts.LIFE_TAG, "$ACTIVITY:onPause")
        super.onPause()
        owner.onPause()
    }

    override fun onStop() {
        Log.e(KConsts.LIFE_TAG, "$ACTIVITY:onStop")
        super.onStop()
        owner.onStop()
    }

    override fun onDestroy() {
        Log.e(KConsts.LIFE_TAG, "$ACTIVITY:onDestroy")
        super.onDestroy()
        owner.onDestroy()
    }

    fun update(view: View) {
        model.nameLiveData.value = User()
    }

    /**
     * 关闭Service
     */
    fun closeService(view: View) {
        val intent = Intent(this, MyService::class.java)
        stopService(intent)
    }

    /**
     * 启动Service
     */
    private fun startLifecycleService() {
        val intent = Intent(this, MyService::class.java)
        startService(intent)
    }

}