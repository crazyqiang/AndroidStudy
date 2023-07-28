package org.ninetripods.mq.study.chatgpt.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import androidx.annotation.IntDef
import io.noties.markwon.Markwon
import io.noties.markwon.ext.latex.JLatexMathPlugin
import io.noties.markwon.ext.latex.JLatexMathTheme
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.inlineparser.MarkwonInlineParserPlugin
import io.noties.markwon.utils.NoCopySpannableFactory
import kotlin.math.abs


/**
 * 自动填充View
 * Created by mq on 2023/5/31
 */
class ChatAutoFillView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyle) {

    companion object {
        const val CURSOR_WIDTH = 8 //光标宽度
        const val REFRESH_INTERVAL = 50L //自动填充刷新间隔
        const val CURSOR_SHOW_INTERVAL = 500L //光标刷新间隔

        //解析的文字样式
        const val TYPE_NORMAL = 1 //正常文字样式
        const val TYPE_IMAGE = 2 //图片样式
        const val TYPE_OCR = 3 //OCR识别样式
        const val TYPE_MARKDOWN = 4 //markdown格式
    }

    private var curFillType = TYPE_MARKDOWN //当前识别类型
    private var mCursorPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }
    private var mCursorRect = RectF()
    private var mCursorState = false

    /**
     * 是否展示光标 等待数据返回时展示，文字自动填充时隐藏
     */
    private var isShowCursor = true

    private var isAutoWriting = false //是否在自动填充
    private var isReplyEnd = false //是否回答完毕
    private var needShowStr = "" //需要绘制的内容
    private var needShowIndex = 0 //需要绘制内容的索引
    private var endCallBack: (() -> Unit)? = null //结束回调
    private var autoFillAnim = true
    private val mTotalBuilder = StringBuilder() //记录当前接收的数据
    private val mDrawBuilder = StringBuilder() //记录要绘制的的数据
    private var isInterrupt = false //是否中途被中断，如用户点击Stop停止返回答案，后续就不再展示了。

    private val markDown = Markwon.builder(context)
        //.usePlugin(IconPlugin.create(IconSpanProvider.create(context, 0))) //支持本地图片解析
        .usePlugin(MarkwonInlineParserPlugin.create())
        .usePlugin(HtmlPlugin.create()) //Html
//        .usePlugin(GlideImagesPlugin.create(object : GlideImagesPlugin.GlideStore {
//            override fun load(drawable: AsyncDrawable): RequestBuilder<Drawable> {
//                return Glide.with(context).load(drawable.destination)
//            }
//
//            override fun cancel(target: Target<*>) {
//                Glide.with(context).clear(target)
//            }
//        })) //Image
//        .usePlugin(LinkifyPlugin.create()) //超链接
        .usePlugin(JLatexMathPlugin.create(textSize) { builder ->
            builder.inlinesEnabled(true)
            //设置Theme相关
            with(builder.theme()) {
                backgroundProvider { ColorDrawable(Color.parseColor("#D0D0D0")) }
                padding(JLatexMathTheme.Padding.all(15))// padding for both inlines and blocks
            }
        })
        .textSetter { textView, spanned, bufferType, onComplete ->
            textView.apply {
                movementMethod = LinkMovementMethod.getInstance()
                setSpannableFactory(NoCopySpannableFactory.getInstance())
                setText(spanned, bufferType)
                onComplete.run()
            }
        }
        .build()

    private val cursorRunnable = object : Runnable {
        override fun run() {
            mCursorState = !mCursorState
            invalidate()
            if (isShowCursor) {
                postDelayed(this, CURSOR_SHOW_INTERVAL)
            }
        }
    }

    private val fillTextRunnable = object : Runnable {
        override fun run() {
            if (isInterrupt) return //中断展示
            if (autoFillAnim) {
                //自动填充效果
                if (needShowIndex < needShowStr.length) {
                    isAutoWriting = true
                    appendText(needShowStr[needShowIndex].toString()) //填充单个字符
                    needShowIndex++
                    postDelayed(this, REFRESH_INTERVAL)
                } else {
                    isAutoWriting = false
                    needShowIndex = 0
                    checkForUpdate()
                }
            } else {
                //不展示自动填充效果
                appendText(needShowStr)
                checkForUpdate()
            }
        }
    }

    init {
        setPadding(30, 15, 30, 15)
        post(cursorRunnable)
    }

    private fun appendText(str: String) {
        val hasDrawStr = mDrawBuilder.append(str).toString()
        when (curFillType) {
            TYPE_NORMAL -> append(str)
            TYPE_MARKDOWN, TYPE_OCR -> {
                markDown.setParsedMarkdown(this, markDown.toMarkdown(hasDrawStr))
            }
            TYPE_IMAGE -> {}
        }
    }

    fun reset() {
        mTotalBuilder.clear()
        mDrawBuilder.clear()
        text = ""
    }

    /**
     * 设置填充完回调
     * @param endBlock
     */
    fun setEndCallback(endBlock: () -> Unit) {
        this.endCallBack = endBlock
    }

    /**
     * 填充文案
     * @param income
     * @param autoFillAnim 是否开启自动填充的效果，默认开启
     */
    fun fillText(
        income: SegModel,
        autoFillAnim: Boolean = true,
        @FillType type: Int = TYPE_MARKDOWN,
    ) {
        val segContent = income.content
        if (segContent?.isEmpty() == true || isInterrupt) return
        mTotalBuilder.append(segContent)
        this.curFillType = type
        this.autoFillAnim = autoFillAnim
        if (!isAutoWriting) {
            checkForUpdate()
        }
        this.isReplyEnd = income.isEnd
    }

    /**
     * 是否在自动填充中
     */
    fun isAutoWriting(): Boolean = isAutoWriting

    fun stop() {
        isInterrupt = true
        isAutoWriting = false
        isReplyEnd = true
        removeCallbacks(fillTextRunnable)
        hideCursor()
    }

    private fun checkForUpdate() {
        val incomeStr = mTotalBuilder.toString()
        val hasDrawStr = mDrawBuilder.toString()
        if (incomeStr == hasDrawStr) {
            if (isReplyEnd) {
                //填充完毕
                hideCursor()
                mTotalBuilder.clear()
                endCallBack?.invoke()
            } else {
                showCursor()
            }
        } else
            if (incomeStr.contains(hasDrawStr)) {
                hideCursor()
                needShowStr = incomeStr.substring(hasDrawStr.length)
                post(fillTextRunnable)
            }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        log("$isShowCursor , $mCursorState $isAutoWriting $isReplyEnd")
        if (isShowCursor && mCursorState && !isAutoWriting && !isReplyEnd) {
            val totalHeight = height - paddingTop - paddingBottom
            val lastLineIndex = layout.lineCount - 1 //最后一行索引
            val cursorX = layout.getLineRight(lastLineIndex) + 10 //光标的X起始位置
            //Descent + Ascent 获取光标高度
            var cursorHeight =
                abs(layout.getLineAscent(lastLineIndex)) + abs(layout.getLineDescent(lastLineIndex))
            //防止最后一行是latex公式导致光标太长
            if (cursorHeight > textSize) {
                cursorHeight = textSize.toInt()
            }
//            val lineHeight = totalHeight.toFloat() / layout.lineCount
//            log("lineHeight:$lineHeight,cursorHeight:$cursorHeight")
            val cursorY = totalHeight - cursorHeight + layout.spacingAdd
            mCursorRect.set(
                cursorX, cursorY, cursorX + CURSOR_WIDTH, totalHeight.toFloat()
            )
            canvas?.drawRect(mCursorRect, mCursorPaint)
        }
    }

    /**
     * 展示光标
     */
    private fun showCursor() {
        isShowCursor = true
        removeCallbacks(cursorRunnable)
        post(cursorRunnable)
    }

    /**
     * 隐藏光标
     */
    private fun hideCursor() {
        isShowCursor = false
        removeCallbacks(cursorRunnable)
    }
}

/**
 * @property content gpt返回的阶段性文案
 * @property isEnd 是否返回完毕
 */
data class SegModel(val content: String?, val isEnd: Boolean)

@Retention(AnnotationRetention.SOURCE)
@IntDef(
    ChatAutoFillView.TYPE_NORMAL,
    ChatAutoFillView.TYPE_IMAGE,
    ChatAutoFillView.TYPE_OCR,
    ChatAutoFillView.TYPE_MARKDOWN
)
annotation class FillType //识别类型