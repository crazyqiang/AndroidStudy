package org.ninetripods.mq.study.jetpack.livedata

import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.CommonWebviewActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.KConsts
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.util.Constant

/**
 * LiveData示例
 */
class LiveDataActivity : BaseActivity() {

    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val mTextView: TextView by id(R.id.tv_text)
    private val mTextViewRight: TextView by id(R.id.tv_text1)
    private val mBtnSwitch: SwitchCompat by id(R.id.btn_switch)
    var mFragment: LiveDataFragment? = null

    override fun setContentView() {
        setContentView(R.layout.activity_live_data)
    }

    override fun initViews() {
        initToolBar(mToolBar, "Jetpack LiveData", true, true, TYPE_BLOG)
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

    //移除Fragment
    fun removeFragment(view: View) {
        val fragment = supportFragmentManager.findFragmentById(R.id.fl_content)
        if (fragment == null) {
            Toast.makeText(this, "没有Fragment", Toast.LENGTH_SHORT).show()
            return
        }
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commitAllowingStateLoss()
    }

    override fun openWebview() {
        CommonWebviewActivity.webviewEntrance(this, Constant.BLOG_JETPACK_LIVEDATA)
    }
}