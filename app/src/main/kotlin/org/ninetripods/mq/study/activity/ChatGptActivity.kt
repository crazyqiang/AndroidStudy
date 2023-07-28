package org.ninetripods.mq.study.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.base.adapter.BaseAdapter
import org.ninetripods.mq.study.base.iInterface.ItemClick
import org.ninetripods.mq.study.chatgpt.ChatVHolderFactory
import org.ninetripods.mq.study.chatgpt.GuideItemModel
import org.ninetripods.mq.study.chatgpt.GuideModel
import org.ninetripods.mq.study.chatgpt.MessageModel
import org.ninetripods.mq.study.kotlin.ktx.id

/**
 * 仿ChatGPT交互页面
 */
class ChatGptActivity : AppCompatActivity(), ItemClick<MessageModel> {

    companion object {
        const val GUIDE_TEXT_1 = "今日天气如何？"
        const val GUIDE_TEXT_2 = "以春天为题写一首诗。"
        const val GUIDE_TEXT_3 = "明天的彩票号码是多少？"
        const val REPLY_TEXT_1 =
            "今天夜间到明天白天:晴间多云，西风一到二级，最高温度:25℃(摄氏度)，最低温度:15℃(摄氏度)。空气质量:优。紫外线指数:强。 "
        const val REPLY_TEXT_2 = "春风拂面花香浓，\n" +
                "轻歌曼舞自心中。\n" +
                "莺啼燕语声声绕，\n" +
                "桃红柳绿画境容。\n" +
                "\n" +
                "山间溪畔寻幽趣，\n" +
                "芳草如茵踏绿途。\n" +
                "细雨濯清新心扉，\n" +
                "梨花雪片点春图。\n" +
                "\n" +
                "春水涟漪波澜动，\n" +
                "微风拂面心弦动。\n" +
                "花开花落轮回处，\n" +
                "岁月循环春永在。\n" +
                "\n" +
                "花开花谢芬芳在，\n" +
                "岁月循环春长在。\n" +
                "春风拂面花香浓，\n" +
                "轻歌曼舞自心中。"
        const val REPLY_TEXT_3 =
            "很抱歉，作为一个语言模型AI，我无法预测未来或者提供未来的信息，包括彩票号码。彩票号码是由随机算法产生的，没有任何规律可循，因此无法预测其结果。购买彩票是一种娱乐方式，但请记得理性购买，不要过度投入金钱。祝您好运！"
    }

    private val mRv: RecyclerView by id(R.id.rv_view)
    private val chatAdapter by lazy { BaseAdapter<MessageModel>(ChatVHolderFactory(), this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_rv)
        mRv.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }
        mRv.adapter = chatAdapter
        setRvInfo()
    }

    private fun setRvInfo() {
        val list = mutableListOf<MessageModel>()
        list.add(MessageModel(content = "hi，there!", type = ChatVHolderFactory.TYPE_REPLY_TXT, autoFillAnim = false))

        list.add(
            MessageModel(
                type = ChatVHolderFactory.TYPE_REPLY_GUIDE,
                innerModel = GuideModel(
                    sceneTypeName = "您可以像下面这样问我：",
                    sceneList = mutableListOf<GuideItemModel>().apply {
                        add(GuideItemModel(sceneName = "今日天气如何？"))
                        add(GuideItemModel(sceneName = "以春天为题写一首诗。"))
                        add(GuideItemModel(sceneName = "明天的彩票号码是多少？"))
                    })
            )
        )

//        list.add(MessageModel(content = "天气预报", type = ChatVHolderFactory.TYPE_ASK_TXT))
//        list.add(MessageModel(content = "天气情况如下：", type = ChatVHolderFactory.TYPE_REPLY_TXT))
//        list.add(MessageModel(type = ChatVHolderFactory.TYPE_REPLY_SPAN))
//        for (i in 0..20) {
//            list.add(MessageModel(content = "天气预报", type = ChatVHolderFactory.TYPE_ASK_TXT))
//        }
        chatAdapter.submitList(list)
    }

    override fun itemClick(itemView: View, model: MessageModel?, position: Int) {
        when (itemView.id) {
            R.id.sample_item -> {
                val guideItemModel: GuideItemModel? =
                    (model?.innerModel as? GuideModel)?.sceneList?.get(position)
                guideItemModel?.let {
                    chatAdapter.addModel(
                        MessageModel(
                            content = it.sceneName,
                            type = ChatVHolderFactory.TYPE_ASK_TXT
                        )
                    )
                    val replyTxt = when (it.sceneName) {
                        GUIDE_TEXT_1 -> REPLY_TEXT_1
                        GUIDE_TEXT_2 -> REPLY_TEXT_2
                        GUIDE_TEXT_3 -> REPLY_TEXT_3
                        else -> REPLY_TEXT_1
                    }
                    chatAdapter.addModel(
                        MessageModel(
                            content = replyTxt,
                            type = ChatVHolderFactory.TYPE_REPLY_TXT
                        )
                    )
                    scrollToBottom()
                }
            }
        }
    }

    private fun scrollToBottom() {
        val position = chatAdapter.itemCount - 1
        mRv.scrollToPosition(position)
    }
}