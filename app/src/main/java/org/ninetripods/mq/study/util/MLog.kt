package org.ninetripods.mq.study.util

import android.text.TextUtils
import android.util.Log

class MLog(val TAG: String = "") {
    companion object {
        const val TAG_DEFAULT = "TTT"
    }

    fun e(content: String) {
        if (!TextUtils.isEmpty(TAG)) {
            Log.e(TAG, content)
        } else {
            Log.e(TAG_DEFAULT, content)
        }
    }
}