package org.ninetripods.mq.study.chatgpt.viewholder

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.base.adapter.BaseVHolder
import org.ninetripods.mq.study.chatgpt.MessageModel

/**
 * Created by mq on 2023/7/20
 */
class ChatReplyTxHolder(
    val context: Context,
    parent: ViewGroup,
    layoutId: Int = R.layout.chat_reply_text_item,
) : BaseVHolder<MessageModel>(context, parent, layoutId) {

    private val sayHello = bind<TextView>(R.id.sayHello)

    override fun onBindView(item: MessageModel, position: Int) {
        sayHello.text = item.content
    }
}