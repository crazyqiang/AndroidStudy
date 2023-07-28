package org.ninetripods.mq.study.chatgpt.viewholder

import android.content.Context
import android.view.ViewGroup
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.base.adapter.BaseVHolder
import org.ninetripods.mq.study.chatgpt.MessageModel
import org.ninetripods.mq.study.chatgpt.widget.ChatAutoFillView
import org.ninetripods.mq.study.kotlin.ktx.log

/**
 * Created by mq on 2023/7/20
 */
class ChatReplyTxHolder(
    val context: Context,
    parent: ViewGroup,
    layoutId: Int = R.layout.chat_reply_text_item,
) : BaseVHolder<MessageModel>(context, parent, layoutId) {

    private val answerText = bind<ChatAutoFillView>(R.id.answerText)
    private var isDrawEnd = false

    override fun onBindView(item: MessageModel, position: Int) {
        log("position:$position, item:${item.content}")
        //TODO 打字机效果待优化
//        answerText.setEndCallback {
//            isDrawEnd = true
//        }
//        answerText.reset()
//        answerText.fillText(
//            income = SegModel(item.content, true),
//            autoFillAnim = item.autoFillAnim && !isDrawEnd,
//            type = ChatAutoFillView.TYPE_MARKDOWN
//        )
        answerText.stop()
        answerText.text = item.content
    }
}