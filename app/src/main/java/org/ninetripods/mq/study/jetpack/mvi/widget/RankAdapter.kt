package org.ninetripods.mq.study.jetpack.mvi.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.mvvm.model.RankItem

class RankAdapter : RecyclerView.Adapter<RankAdapter.RankViewHolder>() {
    var rankModels = mutableListOf<RankItem>()

    class RankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTvText: TextView = itemView.findViewById(R.id.tv_text)
    }

    fun setModels(models: List<RankItem>) {
        rankModels.clear()
        rankModels.addAll(models)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mvi_rv, parent, false)
        return RankViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        holder.mTvText.text = rankModels[position].username
    }

    override fun getItemCount(): Int = rankModels.size
}