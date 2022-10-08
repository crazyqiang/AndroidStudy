package org.ninetripods.mq.study.jetpack.mvi

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.ninetripods.mq.study.R

/**
 * MVI示例
 */
class MviExampleActivity : AppCompatActivity() {

    private val mViewModel: MViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wan_android)
        registerEvent()
    }

    private fun registerEvent() {

    }

}