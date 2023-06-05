package org.ninetripods.mq.study.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.util.sp.FirPreferences
import org.ninetripods.mq.study.util.sp.SecPreferences

/**
 * SharedPreferences使用kotlin委托方式实现：[org.ninetripods.mq.study.util.sp.SharedPreferencesDelegate]
 * Created by mq on 2023/6/5
 */
class SharedPreferencesActivity : AppCompatActivity() {

    /**
     * 支持按不同文件名进行存值
     */
    private val firSp = FirPreferences(this)
    private val secSp = SecPreferences(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //存值
        firSp.isShow = true
        firSp.name = "小马快跑"
        //存值
        secSp.age = 18
        secSp.cost = 123.4f
        //取值
        log("first: isShow -> ${firSp.isShow}, name -> ${firSp.name}")
        log("second: age -> ${secSp.age}, cost -> ${secSp.cost}")
    }
}