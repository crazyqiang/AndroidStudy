package org.ninetripods.mq.study.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.util.sp.CommonPreferences

/**
 * SharedPreferences使用kotlin委托方式实现：[org.ninetripods.mq.study.util.sp.SharedPreferencesDelegate]
 * Created by mq on 2023/6/5
 */
class SharedPreferencesActivity : AppCompatActivity() {

    /**
     * 支持按不同文件名进行存值
     */
    private val spInfo = CommonPreferences(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //存值
        spInfo.isShow = true
        spInfo.name = "小马快跑"
        //存值
        spInfo.age = 18
        spInfo.cost = 123.4f
        spInfo.setString = setOf("一", "二", "三")
        //取值
        log("isShow -> ${spInfo.isShow}, name -> ${spInfo.name}")
        log("age -> ${spInfo.age}, cost -> ${spInfo.cost}，setString -> ${spInfo.setString}")

    }
}