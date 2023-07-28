package org.ninetripods.mq.study.chatgpt.viewholder

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
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
        const val SEGMENT_20 = "今天夜间到明天白天:晴间多云，西风一到二级，最高温度:25℃(摄氏度)，最低温度:15℃(摄氏度)。空气质量:优。紫外线指数:强。 "
        const val SEGMENT_21 = "  点击拍天气"
        const val SEGMENT_22 =
            " 预计25号，受内蒙中东部冷空气影响，气温将骤降至5℃(摄氏度)以下。请观众朋友们在享受阳光沐浴的同时，提前做好防寒保暖工作，谨防不稳定气温给您的出行和身体带来不便。 "
    }

    private val tvImgText: TextView = bind(R.id.tv_img_text)
    private val rvLabel: RecyclerView = bind(R.id.rv_language)
    private val llLabel: LinearLayoutCompat = bind(R.id.ll_label)

    private val noScrollerGridLayoutManager = GridLayoutManager(context, 4)
    private val itemSpace = 10.dp2px()
    private val girdItemDecoration = GirdItemDecoration(itemSpace, itemSpace)

    private val labelAdapter by lazy {
        BaseListAdapter(LabelItemHolder.diffUtil, object : IVHFactory {
            override fun getVH(context: Context, parent: ViewGroup, viewType: Int): BaseVHolder<*> {
                log("方式3：onCreateViewHolder")
                return LabelItemHolder(context, parent)
            }
        })
    }

//    private val labelAdapter by lazy {
//        BaseAdapter<CardItemModel>(object : IVHFactory{
//            override fun getVH(context: Context, parent: ViewGroup, viewType: Int): BaseVHolder<*> {
//                log("方式3：onCreateViewHolder")
//                return LabelItemHolder(context, parent)
//            }
//        })
//    }

    init {
        rvLabel.layoutManager = noScrollerGridLayoutManager
        rvLabel.addItemDecoration(girdItemDecoration)
        rvLabel.adapter = labelAdapter
    }

    private val labels = mutableListOf<CardItemModel>().apply {
        add(CardItemModel().apply { sceneName = "标签1" })
        add(CardItemModel().apply { sceneName = "标签2" })
        add(CardItemModel().apply { sceneName = "标签3" })
        add(CardItemModel().apply { sceneName = "标签4" })
    }

    override fun onBindView(item: MessageModel, position: Int) {
        log("外部Rv---> onBindViewHolder(): $position")
        tvImgText.text = processOnText(SEGMENT_20, SEGMENT_21, SEGMENT_22)
        processLabel() //处理底部标签
    }

    /**
     * 处理标签
     */
    private fun processLabel() {
        //方案2
        llLabel.removeAllViews()
        llLabel.weightSum = 1F
        labels.forEachIndexed { index, it ->
            val itemView =
                LayoutInflater.from(context).inflate(R.layout.chat_reply_language_change_item, null)
            val tv: TextView = itemView.findViewById(R.id.tv_language)
            tv.text = it.sceneName
            log("方式2：LinearLayout.addView $index")
            llLabel.addView(
                itemView,
                LinearLayoutCompat.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1 / labels.size.toFloat()
                ).apply { if (index != labels.lastIndex) marginEnd = 10.dp2px() }
            )
        }

        //方案3
        labelAdapter.submitList(labels)
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

    class LabelItemHolder(
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


        override fun onBindView(item: CardItemModel, position: Int) {
            log("方式3：onBindViewHolder: $position")
            sceneName.text = item.sceneName
        }
    }

}