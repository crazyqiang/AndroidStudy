package org.ninetripods.mq.study.chatgpt.viewholder

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.base.adapter.BaseListAdapter
import org.ninetripods.mq.study.base.adapter.BaseVHolder
import org.ninetripods.mq.study.base.adapter.IVHFactory
import org.ninetripods.mq.study.chatgpt.CardItemModel
import org.ninetripods.mq.study.chatgpt.MessageModel
import org.ninetripods.mq.study.chatgpt.decoration.CenterAlignImgSpan
import org.ninetripods.mq.study.chatgpt.decoration.GirdItemDecoration
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.kotlin.ktx.showToast

/**
 *
 * @param context
 * @param parent
 * @param layoutId
 */
class ChatReplyImgTextHolder(
    val context: Context,
    val parent: ViewGroup,
    private val layoutId: Int = R.layout.chat_reply_rich_text_item,
) : BaseVHolder<MessageModel>(context, parent, layoutId) {

    companion object {
        const val SEGMENT_20 = "Sure, you can "
        const val SEGMENT_21 = "  snap"
        const val SEGMENT_22 = " or type and let me know. I will translate into English. "
    }

    private val tvImgText: TextView = bind(R.id.tv_img_text)
    private val rvLanguage: RecyclerView = bind(R.id.rv_language)

    private val noScrollerGridLayoutManager = GridLayoutManager(context, 4)
    private val itemSpace = 10.dp2px()
    private val girdItemDecoration = GirdItemDecoration(itemSpace, itemSpace)

    private val languageAdapter by lazy {
        BaseListAdapter(LanguageItemHolder.diffUtil, object : IVHFactory {
            override fun getVH(context: Context, parent: ViewGroup, viewType: Int): BaseVHolder<*> {
                return LanguageItemHolder(context, parent)
            }
        })
    }

    init {
        rvLanguage.layoutManager = noScrollerGridLayoutManager
        rvLanguage.addItemDecoration(girdItemDecoration)
        rvLanguage.adapter = languageAdapter
    }

    override fun onBindView(item: MessageModel, position: Int) {
        //val plainTextModel = itemData.getInnerModel(PlainTextModel::class.java)
        log("onBindView position:$position")
        val list = mutableListOf<CardItemModel>().apply {
            add(CardItemModel().apply { sceneName = "Chinese" })
            add(CardItemModel().apply { sceneName = "French" })
            add(CardItemModel().apply { sceneName = "Italian" })
            add(CardItemModel().apply { sceneName = "  ...  " })
        }
        tvImgText.text = processOnText(SEGMENT_20, SEGMENT_21, SEGMENT_22)
        languageAdapter.submit(list)
    }


    /**
     * 相机Icon&文字 图文混排
     * @param str1
     * @param str2
     * @param str3
     */
    private fun processOnText(str1: String, str2: String, str3: String): CharSequence {
        val spanBuilder = SpannableStringBuilder()
        spanBuilder.append(str1).append(str2).append(str3)
        val cameraIcon = ContextCompat.getDrawable(context, R.drawable.icon_photo_logo).apply {
            this?.setBounds(0, 0, 18.dp2px(), 18.dp2px())
        }
        cameraIcon?.let {
            val imgSpan = CenterAlignImgSpan(it)
            val start = str1.length
            spanBuilder.setSpan(imgSpan, start, start + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        val length = str2.length
        val startIndex = spanBuilder.toString().indexOf(str2)
        spanBuilder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                showToast("点击拍照")
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = android.graphics.Color.parseColor("#005CFF")
                ds.isUnderlineText = false
            }
        }, startIndex, startIndex + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvImgText.movementMethod = LinkMovementMethod.getInstance()
        return spanBuilder
    }

    class LanguageItemHolder(
        context: Context,
        parent: ViewGroup,
        layoutId: Int = R.layout.chat_reply_language_change_item,
    ) : BaseVHolder<CardItemModel>(context, parent, layoutId) {

        companion object {
            val diffUtil = object : DiffUtil.ItemCallback<CardItemModel>() {
                override fun areItemsTheSame(
                    oldItem: CardItemModel,
                    newItem: CardItemModel,
                ): Boolean {
                    return oldItem.sceneId == newItem.sceneId
                }

                override fun areContentsTheSame(
                    oldItem: CardItemModel,
                    newItem: CardItemModel,
                ): Boolean {
                    return oldItem.sceneName == newItem.sceneName
                }
            }
        }

        private val sceneName = bind<TextView>(R.id.tv_language)


        override fun onBindView(itemData: CardItemModel, position: Int) {
            log("item onBindView: $position")
            sceneName.text = itemData.sceneName
        }
    }

}