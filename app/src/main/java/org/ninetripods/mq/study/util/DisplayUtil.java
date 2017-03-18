package org.ninetripods.mq.study.util;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * Created by MQ on 2017/3/18.
 */

public class DisplayUtil {
    /**
     * 改变文字颜色
     *
     * @param context       Context
     * @param stringBuilder SpannableStringBuilder
     * @param color         颜色
     * @param start         起始位置
     * @return SpannableStringBuilder
     */
    public static SpannableStringBuilder changeTextColor(Context context, SpannableStringBuilder stringBuilder, int color, int start) {
        stringBuilder.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)), start, stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return stringBuilder;
    }
}
