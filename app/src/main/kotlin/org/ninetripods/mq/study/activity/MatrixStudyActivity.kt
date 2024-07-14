package org.ninetripods.mq.study.activity

import android.graphics.Matrix
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.kotlin.ktx.log

class MatrixStudyActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return super.getLayoutId()
    }

    override fun initEvents() {
        val matrix = Matrix()
        log("matrix = ${matrix.toShortString()}")
    }


}