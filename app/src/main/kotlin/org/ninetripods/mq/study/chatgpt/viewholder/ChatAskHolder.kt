package org.ninetripods.mq.study.chatgpt.viewholder

import android.content.Context
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.base.adapter.BaseVHolder
import org.ninetripods.mq.study.chatgpt.MessageModel
import org.ninetripods.mq.study.kotlin.ktx.gone
import org.ninetripods.mq.study.kotlin.ktx.invisible
import org.ninetripods.mq.study.kotlin.ktx.visible

/**
 * Created by mq on 2023/7/20
 */
class ChatAskHolder(
    val context: Context,
    parent: ViewGroup,
    layoutId: Int = R.layout.chat_ask_text_item,
) : BaseVHolder<MessageModel>(context, parent, layoutId) {

    private val mTvMsg = bind<TextView>(R.id.chat_ask_message)
    private val mTvProgress = bind<ProgressBar>(R.id.chat_ask_message_progress)
    private val mTvError = bind<ImageView>(R.id.chat_ask_message_error)

    override fun onBindView(item: MessageModel, position: Int) {
        updateLoadStatus(item)
        mTvMsg.text = item.content
    }

    private fun updateLoadStatus(itemData: MessageModel) {
        when (itemData.status) {
            //传输中 = 0
            0 -> {
                mTvProgress.invisible()
                mTvError.gone()
            }
            //传输成功 = 1
            1 -> {
                mTvProgress.invisible()
                mTvError.gone()
            }
            //传输失败 = 2
            2 -> {
                mTvProgress.invisible()
                mTvError.visible()
            }
        }
    }
}