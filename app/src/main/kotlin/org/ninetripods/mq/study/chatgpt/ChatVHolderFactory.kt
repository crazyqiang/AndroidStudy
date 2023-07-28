package org.ninetripods.mq.study.chatgpt

import android.content.Context
import android.view.ViewGroup
import org.ninetripods.mq.study.base.adapter.BaseVHolder
import org.ninetripods.mq.study.base.adapter.IVHFactory
import org.ninetripods.mq.study.chatgpt.viewholder.ChatAskHolder
import org.ninetripods.mq.study.chatgpt.viewholder.ChatReplyGuideHolder
import org.ninetripods.mq.study.chatgpt.viewholder.ChatReplyImgTextHolder
import org.ninetripods.mq.study.chatgpt.viewholder.ChatReplyTxHolder

/**
 * Created by mq on 2023/7/23
 */
class ChatVHolderFactory : IVHFactory {
    companion object {
        const val TYPE_ASK_TXT = 1 //type1
        const val TYPE_REPLY_TXT = 2 //type2
        const val TYPE_REPLY_SPAN = 3 //type3
        const val TYPE_REPLY_GUIDE = 4//type4
    }

    override fun getVH(context: Context, parent: ViewGroup, viewType: Int): BaseVHolder<*> {
        return when (viewType) {
            TYPE_ASK_TXT -> ChatAskHolder(context, parent)
            TYPE_REPLY_TXT -> ChatReplyTxHolder(context, parent)
            TYPE_REPLY_SPAN -> ChatReplyImgTextHolder(context, parent)
            TYPE_REPLY_GUIDE -> ChatReplyGuideHolder(context, parent)
            else -> throw IllegalStateException("unSupport type")
        }
    }
}