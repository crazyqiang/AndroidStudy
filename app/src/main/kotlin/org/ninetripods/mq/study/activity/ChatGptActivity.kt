package org.ninetripods.mq.study.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.base.adapter.BaseAdapter
import org.ninetripods.mq.study.chatgpt.ChatVHolderFactory
import org.ninetripods.mq.study.chatgpt.MessageModel
import org.ninetripods.mq.study.kotlin.ktx.id

/**
 * 仿ChatGPT交互页面
 */
class ChatGptActivity : AppCompatActivity() {

    private val mRv: RecyclerView by id(R.id.rv_view)
    private val chatAdapter by lazy { BaseAdapter<MessageModel>(ChatVHolderFactory()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_rv)
        setRvInfo()
    }

    private fun setRvInfo() {
        val list = mutableListOf<MessageModel>()
        list.add(MessageModel("1", "今天天气如何", type = ChatVHolderFactory.TYPE_ASK_TXT))
        list.add(MessageModel("1", "今天天气很棒", type = ChatVHolderFactory.TYPE_REPLY_TXT))
        list.add(MessageModel("1", "今天天气如何", type = ChatVHolderFactory.TYPE_REPLY_SPAN))
        for (i in 0..50) {
            list.add(MessageModel("1", "今天天气如何", type = ChatVHolderFactory.TYPE_ASK_TXT))
        }
        chatAdapter.submitList(list)
        mRv.layoutManager = LinearLayoutManager(this)
        mRv.adapter = chatAdapter
    }
}