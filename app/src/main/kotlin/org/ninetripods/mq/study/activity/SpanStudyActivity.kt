package org.ninetripods.mq.study.activity

import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.showToast
import org.ninetripods.mq.study.span.CenterImageSpan
import org.ninetripods.mq.study.span.CustomClickSpan
import org.ninetripods.mq.study.span.RelativeSizeColorSpan

class SpanStudyActivity : BaseActivity() {

    companion object {
        private const val TITLE_1 = "---> 1、影响外观的Span, 实现UpdateAppearance接口，继承CharacterStyle \n"
        const val TITLE_2 =
            "---> 2、影响度量的Span，实现UpdateLayout接口(UpdateLayout->UpdateAppearance)，继承MetricAffectingSpan(MetricAffectingSpan->CharacterStyle)\n"
        const val TITLE_3 = "---> 3、影响段落的Span \n"

        private const val SPAN_STR: String = TITLE_1 +
                "北国风光，千里冰封，万里雪飘。\n\n" +
                "望长城内外，惟余莽莽；大河上下，顿失滔滔。\n\n山舞银蛇，原驰蜡象，欲与天公试比高。\n\n" +
                TITLE_2 +
                "须晴日，看红装素裹，_ 分外妖娆。\n\n江山如此多娇，引无数英雄竞折腰。\n\n" +
                "惜秦皇汉武，略输文采；唐宗宋祖，稍逊风骚。\n\n" +
                TITLE_3 +
                "一代天骄，成吉思汗，只识弯弓射大雕。\n\n" + " 俱往矣，数风流人物，还看今朝。"
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
        const val SEG_13 = "_ 分外妖娆"
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

        val finalStr = SPAN_STR.replace(SEG_3, "万里雪飘 ")
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
        val indexTitle1 = finalStr.indexOf(TITLE_1)
        val indexTitle2 = finalStr.indexOf(TITLE_2)
        val indexTitle3 = finalStr.indexOf(TITLE_3)
    }

    private val tvSpan: TextView by id(R.id.tv_span)
    private val tvOrigin: TextView by id(R.id.tv_span_flag)
    private val tvFlagsEE: TextView by id(R.id.tv_span_flag_e_e)
    private val tvFlagsII: TextView by id(R.id.tv_span_flag_i_i)
    private val tvFlagsEI: TextView by id(R.id.tv_span_flag_e_i)
    private val tvFlagsIE: TextView by id(R.id.tv_span_flag_i_e)

    override fun getLayoutId(): Int = R.layout.activity_span_study

    override fun initEvents() {
        /**
         * 测试setSpan(Object what, int start, int end, int flags)中flags的作用
         */
        getSpanFlagStr(insert = false, spanFlag = -1, tv = tvOrigin)
        getSpanFlagStr(spanFlag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE, tv = tvFlagsEE)
        getSpanFlagStr(spanFlag = Spanned.SPAN_INCLUSIVE_INCLUSIVE, tv = tvFlagsII)
        getSpanFlagStr(spanFlag = Spanned.SPAN_EXCLUSIVE_INCLUSIVE, tv = tvFlagsEI)
        getSpanFlagStr(spanFlag = Spanned.SPAN_INCLUSIVE_EXCLUSIVE, tv = tvFlagsIE)

        val spanBuilder = SpannableStringBuilder(finalStr)
        //1、影响外观的Span, 实现UpdateAppearance接口，继承CharacterStyle
        processAppearance(spanBuilder)
        //2、影响度量的Span，实现UpdateLayout接口(UpdateLayout->UpdateAppearance)，
        // 继承MetricAffectingSpan(MetricAffectingSpan->CharacterStyle)
        processMetrics(spanBuilder)
        //3、影响段落的Span
        processParagraph(spanBuilder)

        tvSpan.highlightColor = Color.TRANSPARENT
        tvSpan.movementMethod = LinkMovementMethod.getInstance()
        tvSpan.text = spanBuilder
        tvSpan.setText(spanBuilder, TextView.BufferType.SPANNABLE)
        tvSpan.setOnClickListener {
            //避免ClickSpan与TextView本身同时相应点击事件
            (it as? TextView)?.let { tv ->
                if (tv.selectionStart != -1 || tv.selectionEnd != -1)
                    return@setOnClickListener
            }
            showToast("TextView整体的点击事件触发了")
        }
    }

    /**
     * 影响外观的Span
     */
    private fun processAppearance(spanBuilder: SpannableStringBuilder) {
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

        /**
         * MaskFilterSpan(MaskFilter filter)设置滤镜Span，参数filter是MaskFilter类型：
         * BlurMaskFilter是一个可以应用于Paint的一个滤镜。它可以将绘制的图形进行模糊处理，以达到一些特定的视觉效果。
         * 1、radius ： 模糊半径。数值越大，模糊的范围越广。
         * 2、style是BlurMaskFilter.Blur枚举，表示模糊的类型，包括以下四种：
         *     a、NORMAL：普通模糊，应用于整个图像。
         *     b、SOLID：只绘制模糊的区域，其他区域为透明。
         *     c、OUTER：外部模糊，只在图像外部模糊。
         *     d、INNER：内部模糊，只在图像内部模糊。
         */
        spanBuilder.setSpan(
            //1、NORMAL：在原始边界内外都进行模糊处理。
            MaskFilterSpan(BlurMaskFilter(20F, BlurMaskFilter.Blur.NORMAL)),
            index4, index4 + SEG_4.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanBuilder.setSpan(
            //2、SOLID：在原始边界内部填充实心颜色，在边界外进行模糊处理。
            MaskFilterSpan(BlurMaskFilter(20F, BlurMaskFilter.Blur.SOLID)),
            index5, index5 + SEG_5.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanBuilder.setSpan(
            //3、OUTER：在边界内部不进行绘制，在边界外进行模糊处理。
            MaskFilterSpan(BlurMaskFilter(2F, BlurMaskFilter.Blur.OUTER)),
            index6, index6 + SEG_6.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanBuilder.setSpan(
            //4、INNER：在边界内部进行模糊处理，在边界外不进行绘制。
            MaskFilterSpan(BlurMaskFilter(20F, BlurMaskFilter.Blur.INNER)),
            index7, index7 + SEG_7.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spanBuilder.setSpan(
            StrikethroughSpan(),
            index8, index8 + SEG_8.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        /**
         * 注意点击相关的必须设置textView.movementMethod = LinkMovementMethod.getInstance()，否则点击不生效
         */
        spanBuilder.setSpan(
            CustomClickSpan {
                showToast("自定义ClickableSpan点击: $SEG_9")
            },
            index9, index9 + SEG_9.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanBuilder.setSpan(
            URLSpan("https://www.baidu.com"),
            index10, index10 + SEG_10.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spanBuilder.setSpan(
            ForegroundColorSpan(Color.BLUE),
            indexTitle1, indexTitle1 + TITLE_1.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanBuilder.setSpan(
            ForegroundColorSpan(Color.BLUE),
            indexTitle2, indexTitle2 + TITLE_2.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spanBuilder.setSpan(
            ForegroundColorSpan(Color.BLUE),
            indexTitle3, indexTitle3 + TITLE_3.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    //2、影响度量的Span，实现UpdateLayout接口(UpdateLayout->UpdateAppearance)，
    // 继承MetricAffectingSpan(MetricAffectingSpan->CharacterStyle)
    private fun processMetrics(spanBuilder: SpannableStringBuilder) {
        //设置StyleSpan：加粗、斜体等，如Typeface.NORMAL、BOLD、ITALIC、BOLD_ITALIC
        spanBuilder.setSpan(
            StyleSpan(Typeface.BOLD_ITALIC),
            index11, index11 + SEG_11.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //设置不同样式的文本字体
        val customTypeface = Typeface.create(
            ResourcesCompat.getFont(this, R.font.iconfont_guide),
            Typeface.BOLD_ITALIC
        )
        /**
         * TypefaceSpan构造函数有两个：
         * 1、TypefaceSpan(String family)字体族，最终通过Typeface.create(family, style)创建Typeface
         * 2、TypefaceSpan(Typeface typeface)直接传入Typeface
         * 当通过TypefaceSpan(String family)创建时，以前的Typeface都会保留并与新创建的合并；而通过TypefaceSpan(Typeface typeface)创建的会直接覆盖之前的Typeface设置。
         */
        spanBuilder.setSpan(
            //TypefaceSpan(customTypeface), //API>=28以上才有
            TypefaceSpan("serif"), //系统自带的有normal，sans，monospace，serif
            index12, index12 + SEG_12.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val imgDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.icon_flower, null)
        imgDrawable?.let {
            it.setBounds(0, 0, 40.dp2px(), 40.dp2px())
            spanBuilder.setSpan(
                CenterImageSpan(it),
                index13, index13 + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        //自定义RelativeSizeSpan
        spanBuilder.setSpan(
            RelativeSizeColorSpan(0.8f, Color.RED),
            index14, index14 + SEG_14.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spanBuilder.setSpan(
            AbsoluteSizeSpan(20, true),
            index15, index15 + SEG_15.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spanBuilder.setSpan(
            ScaleXSpan(2f),
            index16, index16 + SEG_16.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //将文本baseline的位置向下移动
        spanBuilder.setSpan(
            SubscriptSpan(),
            index17 + SEG_17.length / 2, index17 + SEG_17.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        //将文本baseline的位置向上移动
        spanBuilder.setSpan(
            SuperscriptSpan(),
            index18 + SEG_18.length / 2, index18 + SEG_18.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spanBuilder.setSpan(
            TextAppearanceSpan(this, R.style.textAppearanceSpan),
            index19, index19 + SEG_19.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    //3、影响段落的Span
    private fun processParagraph(spanBuilder: SpannableStringBuilder) {
        spanBuilder.setSpan(
            /**
             * first: 每一个段落的首行缩进
             * mRest: 每一个段落的其他行缩进
             */
            LeadingMarginSpan.Standard(20.dp2px(), 5.dp2px()),
            0, SPAN_STR.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //LineBackgroundSpan改变行的背景色，如果[start,end)不够一行按一行处理
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            spanBuilder.setSpan(
                LineBackgroundSpan.Standard(Color.YELLOW),
                index20, index20 + SEG_20.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        //LineHeightSpan改变段落的行高
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //注意，LineHeightSpan将改变整个段落的行高，即使它只覆盖段落的一部分。
            spanBuilder.setSpan(
                LineHeightSpan.Standard(30.dp2px()),
                index21, index21 + SEG_21.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        //在文本的行首添加一个带有指定边距的 Drawable  TODO IconMarginSpan vs DrawableMarginSpan如何使用
//        val imgDrawable =
//            ResourcesCompat.getDrawable(resources, R.drawable.icon_flower, null).apply {
//                this?.setBounds(0, 0, 20.dp2px(), 20.dp2px())
//            }
//        spanBuilder.setSpan(
//            IconMarginSpan(BitmapFactory.decodeResource(resources, R.drawable.icon_flower), 10.sp2px()),
//            //DrawableMarginSpan(imgDrawable!!, 10.sp2px()),
//            index22, index22 + SEG_22.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//        )

        //QuoteSpan给文本添加引用样式。
        //注意：必须从一个段落的第一个字符连接到最后一个字符，否则不会显示该span。
        spanBuilder.setSpan(
            QuoteSpan(Color.RED),
            index23 - 1, SPAN_STR.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        //给文本添加项目符号样式 TODO
        spanBuilder.setSpan(
            BulletSpan(20, 0xCE6454A),
            index24, index24 + SEG_24.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        //AlignmentSpan
        spanBuilder.setSpan(
            AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE),
            index25, index25 + SEG_25.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        //TabStopSpan
        spanBuilder.setSpan(
            TabStopSpan.Standard(20.dp2px()),
            index25, index25 + SEG_25.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

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