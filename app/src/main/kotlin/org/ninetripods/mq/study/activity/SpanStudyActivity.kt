package org.ninetripods.mq.study.activity

import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.showToast
import org.ninetripods.mq.study.span.CustomClickSpan

class SpanStudyActivity : BaseActivity() {

    companion object {
        const val SPAN_STR: String = "北国风光，千里冰封，万里雪飘。\n" +
                "望长城内外，惟余莽莽；大河上下，顿失滔滔。\n" +
                "山舞银蛇，原驰蜡象，欲与天公试比高。\n" +
                "须晴日，看红装素裹，分外妖娆。\n" +
                "江山如此多娇，引无数英雄竞折腰。\n" +
                "惜秦皇汉武，略输文采；唐宗宋祖，稍逊风骚。\n" +
                "一代天骄，成吉思汗，只识弯弓射大雕。\n" +
                "俱往矣，数风流人物，还看今朝。"
        const val SEG_1 = "北国风光"
        const val SEG_2 = "千里冰封"
        const val SEG_3 = "万里雪飘"
        const val SEG_4 = "望长城内外"
        const val SEG_5 = "惟余莽莽"
        const val SEG_6 = "大河上下"
        const val SEG_7 = "顿失滔滔"
        const val SEG_8 = "山舞银蛇"
        const val SEG_9 = "原驰蜡象"
        const val SEG_10 = "欲与天公试比高"
        const val SEG_11 = "须晴日"
        const val SEG_12 = "看红装素裹"
        const val SEG_13 = "分外妖娆"
        const val SEG_14 = "江山如此多娇"
        const val SEG_15 = "引无数英雄竞折腰"
        const val SEG_16 = "惜秦皇汉武"
        const val SEG_17 = "略输文采"
        const val SEG_18 = "唐宗宋祖"
        const val SEG_19 = "稍逊风骚"
        const val SEG_20 = "一代天骄"
        const val SEG_21 = "成吉思汗"
        const val SEG_22 = "只识弯弓射大雕"
        const val SEG_23 = "俱往矣"
        const val SEG_24 = "数风流人物"
        const val SEG_25 = "还看今朝"
        const val ORIGIN_FLAGS = "01234"
    }

    private val tvSpan: TextView by id(R.id.tv_span)
    private val tvOrigin: TextView by id(R.id.tv_span_flag)
    private val tvFlagsEE: TextView by id(R.id.tv_span_flag_e_e)
    private val tvFlagsII: TextView by id(R.id.tv_span_flag_i_i)
    private val tvFlagsEI: TextView by id(R.id.tv_span_flag_e_i)
    private val tvFlagsIE: TextView by id(R.id.tv_span_flag_i_e)


    private val sBuilder = StringBuilder()

    override fun getLayoutId(): Int = R.layout.activity_span_study

    @RequiresApi(Build.VERSION_CODES.P)
    override fun initEvents() {

        /**
         * 测试setSpan(Object what, int start, int end, int flags)中flags的作用
         */
        getSpanFlagStr(insert = false, spanFlag = -1, tv = tvOrigin)
        getSpanFlagStr(spanFlag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, tv = tvFlagsEE)
        getSpanFlagStr(spanFlag = Spanned.SPAN_INCLUSIVE_INCLUSIVE, tv = tvFlagsII)
        getSpanFlagStr(spanFlag = Spanned.SPAN_EXCLUSIVE_INCLUSIVE, tv = tvFlagsEI)
        getSpanFlagStr(spanFlag = Spanned.SPAN_INCLUSIVE_EXCLUSIVE, tv = tvFlagsIE)

        val finalStr = SPAN_STR.replace(SEG_3, "万里雪飘 ")
        val spanBuilder = SpannableStringBuilder(finalStr)

        //TODO
        //context.resources.getColor(R.color.CE6454A)
        //context.getColor(R.color.CE6454A)

        val index1 = finalStr.indexOf(SEG_1)
        val index2 = finalStr.indexOf(SEG_2)
        val index3 = finalStr.indexOf(SEG_3)
        val index4 = finalStr.indexOf(SEG_4)
        val index5 = finalStr.indexOf(SEG_5)
        val index6 = finalStr.indexOf(SEG_6)
        val index7 = finalStr.indexOf(SEG_7)
        val index8 = finalStr.indexOf(SEG_8)
        val index9 = finalStr.indexOf(SEG_9)
        val index10 = finalStr.indexOf(SEG_10)
        val index11 = finalStr.indexOf(SEG_11)
        val index12 = finalStr.indexOf(SEG_12)
        val index13 = finalStr.indexOf(SEG_13)
        val index14 = finalStr.indexOf(SEG_14)
        val index15 = finalStr.indexOf(SEG_15)
        val index16 = finalStr.indexOf(SEG_16)
        val index17 = finalStr.indexOf(SEG_17)
        val index18 = finalStr.indexOf(SEG_18)
        val index19 = finalStr.indexOf(SEG_19)
        val index20 = finalStr.indexOf(SEG_20)
        val index21 = finalStr.indexOf(SEG_21)
        val index22 = finalStr.indexOf(SEG_22)
        val index23 = finalStr.indexOf(SEG_23)
        val index24 = finalStr.indexOf(SEG_24)
        val index25 = finalStr.indexOf(SEG_25)

        /**
         * Object what, int start, int end, int flags
         * what：指的是要应用于文本的范围，
         * start和end：表明要应用范围的文本部分，默认是左闭右开区间，如[0，1)。
         * flags：应用Span后，如果在Span边界（即在开始索引或结束索引处）内继续插入文本，则Span会自动扩展以包含插入的文本，flags参数确定Span是否应扩展以包含插入的文本。
         * - Spanned.SPAN_INCLUSIVE_INCLUSIVE 包含两端端点，即左闭右闭区间[start, end]
         * - Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 不包含两端start和end所在的端点，即左开右开区间(start, end)。
         * - Spanned.SPAN_INCLUSIVE_EXCLUSIVE 左闭右开区间，即[start, end)
         * - Spanned.SPAN_EXCLUSIVE_INCLUSIVE 左开右闭区间，即(start, end]
         */
        spanBuilder.setSpan(
            ForegroundColorSpan(Color.RED),
            index1, index1 + SEG_1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanBuilder.setSpan(
            BackgroundColorSpan(Color.GRAY),
            index2, index2 + SEG_2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanBuilder.setSpan(
            UnderlineSpan(), index3, index3 + SEG_3.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        val imgDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.icon_qrcode_we_chat, null).apply {
                this?.setBounds(0, 0, 18.dp2px(), 18.dp2px())
            }
        imgDrawable?.let {
            spanBuilder.setSpan(
                ImageSpan(it, DynamicDrawableSpan.ALIGN_BASELINE),
                index3 + 4,
                index3 + 5,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        /**
         * 1、NORMAL：在原始边界内外都进行模糊处理。
         * 2、SOLID：在原始边界内部填充实心颜色，在边界外进行模糊处理。
         * 3、OUTER：在原始边界内部不进行绘制，在边界外进行模糊处理。
         * 4、INNER：在原始边界内部进行模糊处理，在边界外不进行绘制。
         */
        spanBuilder.setSpan(
            MaskFilterSpan(BlurMaskFilter(20F, BlurMaskFilter.Blur.SOLID)),
            index4, index4 + SEG_4.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spanBuilder.setSpan(
            StrikethroughSpan(),
            index5, index5 + SEG_5.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        //设置StyleSpan：加粗、斜体等
        spanBuilder.setSpan(
            StyleSpan(Typeface.BOLD_ITALIC),
            index6, index6 + SEG_6.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        //设置不同样式的文本字体
        //API>=28以上才有
        val myTypeface = Typeface.create(
            ResourcesCompat.getFont(this, R.font.iconfont_guide),
            Typeface.ITALIC
        )
        spanBuilder.setSpan(
            //TypefaceSpan(myTypeface),
            TypefaceSpan("serif"),
            index7, index7 + SEG_7.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //注意必须设置tvSpan.movementMethod = LinkMovementMethod.getInstance()，否则点击不生效
        spanBuilder.setSpan(
            URLSpan("https://www.baidu.com"),
            index8, index8 + SEG_8.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //必须设置TextView的movementMethod才会生效
        spanBuilder.setSpan(
            CustomClickSpan {
                showToast("自定义ClickableSpan: $SEG_9")
            },
            index9, index9 + SEG_9.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvSpan.highlightColor = Color.TRANSPARENT
        tvSpan.movementMethod = LinkMovementMethod.getInstance()
        tvSpan.text = spanBuilder
        tvSpan.setOnClickListener {
            //避免ClickSpan与TextView本身同时相应点击事件
            (it as? TextView)?.let { tv ->
                if (tv.selectionStart != -1 || tv.selectionEnd != -1)
                    return@setOnClickListener
            }
            showToast("TextView整体的点击事件触发了")
        }
    }

    private fun getSpanFlagStr(
        insert: Boolean = true,
        spanFlag: Int,
        tv: TextView,
    ) {
        val start = 1
        val end = 3
        // set the span
        val spannableString = SpannableStringBuilder(ORIGIN_FLAGS + getIntroduce(spanFlag))
        val foregroundSpan = ForegroundColorSpan(Color.RED)
        spannableString.setSpan(foregroundSpan, start, end, spanFlag)
        if (insert) {
            // insert the text after the span has already been set
            // (inserting at start index second so that end index doesn't get messed up)
            spannableString.insert(end, "x")
            spannableString.insert(start, "x")
        }
        tv.text = spannableString
    }

    private fun getIntroduce(spanFlag: Int): String {
        return when (spanFlag) {
            Spanned.SPAN_INCLUSIVE_INCLUSIVE -> "-> SPAN_INCLUSIVE_INCLUSIVE"
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE -> "-> SPAN_EXCLUSIVE_EXCLUSIVE"
            Spanned.SPAN_EXCLUSIVE_INCLUSIVE -> "-> SPAN_EXCLUSIVE_INCLUSIVE"
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE -> "-> SPAN_INCLUSIVE_EXCLUSIVE"
            else -> "   -> setSpan()之后不再insert"
        }
    }

}