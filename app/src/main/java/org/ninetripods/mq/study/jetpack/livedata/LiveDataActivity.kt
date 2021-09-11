package org.ninetripods.mq.study.jetpack.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.KConsts

class LiveDataActivity : AppCompatActivity() {

    lateinit var mTextView: TextView
    lateinit var mTextViewRight: TextView
    var mFragment: LiveDataFragment? = null
    lateinit var mBtnSwitch: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)
        mTextView = findViewById(R.id.tv_text)
        mTextViewRight = findViewById(R.id.tv_text1)

        mBtnSwitch = findViewById(R.id.btn_switch)
        mBtnSwitch.setOnCheckedChangeListener { _, isChecked ->
            //发送开关状态 用以在Transformations.switchMap中切换数据源
            LiveDataInstance.SWITCH.value = isChecked
        }
        addLiveDataFragment()
    }

    override fun onStop() {
        super.onStop()
        val data = produceData(true)
        Log.e(KConsts.LIVE_DATA, "onStop():$data")
        sendData(data, true)
    }

    fun updateValue(view: View) {
        sendData(produceData(true), true)
    }

    fun updateValueRight(view: View) {
        sendData(produceData(false), false)
    }

    //随机更新一个整数
    private fun produceData(isLeft: Boolean): String {
        val randomValue = (0..1000).random().toString()
        if (isLeft) {
            mTextView.text = "Activity中发送：$randomValue"
        } else {
            mTextViewRight.text = "Activity中发送：$randomValue"
        }
        //发送开关状态 用以在Transformations.switchMap中切换数据源
        LiveDataInstance.SWITCH.value = mBtnSwitch.isChecked
        return randomValue
    }

    //通过setValue发送更新
    private fun sendData(randomValue: String, isLeft: Boolean) {
        if (isLeft) {
            LiveDataInstance.INSTANCE.value = randomValue
        } else {
            LiveDataInstance.INSTANCE2.value = randomValue
        }
    }

    //添加Fragment
    fun addFragment(view: View) {
        addLiveDataFragment()
    }

    //移除Fragment
    fun removeFragment(view: View) {
        delLiveDataFragment()
    }

    private fun addLiveDataFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fl_content)
        if (fragment != null) {
            Toast.makeText(this, "请勿重复添加", Toast.LENGTH_SHORT).show()
            return
        }

        if (mFragment == null) {
            mFragment = LiveDataFragment.newInstance()
        }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_content, mFragment!!)
            .commitAllowingStateLoss()
    }

    private fun delLiveDataFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fl_content)
        if (fragment == null) {
            Toast.makeText(this, "没有Fragment", Toast.LENGTH_SHORT).show()
            return
        }
        supportFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
    }

}