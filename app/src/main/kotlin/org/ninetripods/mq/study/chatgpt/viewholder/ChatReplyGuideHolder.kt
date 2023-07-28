package org.ninetripods.mq.study.chatgpt.viewholder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.base.adapter.BaseAdapter
import org.ninetripods.mq.study.base.adapter.BaseVHolder
import org.ninetripods.mq.study.base.adapter.IVHFactory
import org.ninetripods.mq.study.base.iInterface.ItemClick
import org.ninetripods.mq.study.base.iInterface.ItemClickListener
import org.ninetripods.mq.study.chatgpt.GuideItemModel
import org.ninetripods.mq.study.chatgpt.GuideModel
import org.ninetripods.mq.study.chatgpt.MessageModel
import org.ninetripods.mq.study.chatgpt.decoration.LinearItemDecoration
import org.ninetripods.mq.study.kotlin.ktx.dp2px

/**
 * Created by mq on 2023/7/20
 */
class ChatReplyGuideHolder(
    val context: Context,
    val parent: ViewGroup,
    layoutId: Int = R.layout.chat_reply_guide_item,
) : BaseVHolder<MessageModel>(context, parent, layoutId) {

    private val guideSceneName = bind<TextView>(R.id.sample_scene_name)

    private val guideRv = bind<RecyclerView>(R.id.sample_recyclerview)
    private val itemSpace = 10.dp2px()
    private val linearItemDecoration = LinearItemDecoration(itemSpace)

    private val guideAdapter by lazy {
        BaseAdapter(object : IVHFactory {
            override fun getVH(context: Context, parent: ViewGroup, viewType: Int): BaseVHolder<*> {
                return GuideItemHolder(context, parent)
            }
        }, object : ItemClick<GuideItemModel> {
            override fun itemClick(itemView: View, model: GuideItemModel?, position: Int) {
                onItemClickListener.itemClick?.itemClick(
                    itemView,
                    onItemClickListener.model,
                    position
                )
            }
        })
    }

    init {
        guideRv.run {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(linearItemDecoration)
            adapter = guideAdapter
        }

    }

    override fun onBindView(item: MessageModel, position: Int) {
        val itemModel = item.innerModel as? GuideModel
        itemModel?.let {
            guideSceneName.text = it.sceneTypeName
            it.sceneList?.let { models -> guideAdapter.submitList(models) }
        }
    }

    private class GuideItemHolder(
        context: Context,
        parent: ViewGroup,
        layoutId: Int = R.layout.chat_sample_item,
    ) : BaseVHolder<GuideItemModel>(context, parent, layoutId) {

        private val sceneTypeName = bind<TextView>(R.id.sceneTypeName)
        private val flGuide: View = bind(R.id.sample_item)

        override fun onBindVHClick(clickListener: ItemClickListener<GuideItemModel>) {
            flGuide.setOnClickListener(clickListener)
        }

        override fun onBindView(item: GuideItemModel, position: Int) {
            sceneTypeName.text = item.sceneName
        }
    }

}
