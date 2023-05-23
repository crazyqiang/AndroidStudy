package org.ninetripods.mq.study.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity Result API使用示例
 * Created by mq on 2023/5/12
 */
class ResultApi2Activity : AppCompatActivity() {
    companion object{
        const val KEY_TRANSFER = "key_transfer"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(RESULT_OK, intent.putExtra(KEY_TRANSFER, "i'm value from ResultApi2Activity"))
    }
}