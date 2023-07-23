package org.ninetripods.mq.study.chatgpt

import org.ninetripods.mq.study.base.adapter.IMultiType

/**
 * Created by mq on 2023/7/20
 */
data class MessageModel(
    var msgId: String,
    var content: String,
    //封禁状态 正常 = 1 全屏蔽 = 2 部分屏蔽 = 3 无需审核 = 4 用户被封禁 = 5
    var banStatus: Int = 1,
    //传输中 = 0, 传输成功 = 1, 传输失败 = 2
    var status: Int = 0,
    var type: Int = ChatVHolderFactory.TYPE_ASK_TXT,
) : IMultiType {
    override fun getItemViewType(): Int = type
}

data class CardItemModel(
    var action: Int = 0,
    var sceneId: Int = 0,
    var sceneName: String = "",
)