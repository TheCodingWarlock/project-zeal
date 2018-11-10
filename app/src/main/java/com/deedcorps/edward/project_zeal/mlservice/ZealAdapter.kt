package com.deedcorps.edward.project_zeal.mlservice

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.deedcorps.edward.project_zeal.R
import com.deedcorps.edward.project_zeal.api.model.Content
import kotlinx.android.synthetic.main.article_item.view.*

class ZealAdapter : ListAdapter<Content, ZealViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ZealViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.article_item, viewGroup, false)
        return ZealViewHolder(view)
    }

    override fun onBindViewHolder(holder: ZealViewHolder, position: Int) {
        holder.bind(getItem(position) as Content)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(firstContent: Content, secondContent: Content): Boolean {
                return firstContent == secondContent
            }

            override fun areContentsTheSame(firstContent: Content, secondContent: Content): Boolean {
                return firstContent.keywords == secondContent.keywords
            }

        }
    }
}

class ZealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(content: Content) {
        val score: Double = Math.nextUp(content.score)
        itemView.scoreTextView.text = "Score: $score"
        itemView.decisionTextView.text = "Data State: ${content.decision}"
    }
}