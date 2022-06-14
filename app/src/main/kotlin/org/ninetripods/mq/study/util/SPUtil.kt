package org.ninetripods.mq.study.util

import android.content.Context

class SPUtil {

    fun aaa(context: Context) {
        val sp = context.getSharedPreferences("", Context.MODE_PRIVATE)
        sp.edit().putBoolean("", true).apply()
    }

}