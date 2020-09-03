package com.lowbottgames.reader.hackernews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lowbottgames.reader.hackernews.R
import com.lowbottgames.reader.hackernews.model.HNItem

class PostsAdapter() : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    var items: List<HNItem>? = null
    var listener: PostsAdapterListener? = null

    var loading = false

    class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewIndex: TextView  = itemView.findViewById(R.id.textView_index)
        private val textViewTitle: TextView  = itemView.findViewById(R.id.textView_title)
        private val textViewSubtitle: TextView  = itemView.findViewById(R.id.textView_subtitle)

        fun bind(item: HNItem) {
            textViewIndex.text = "${(adapterPosition + 1)}"
            textViewTitle.text = item.title
            textViewSubtitle.text = item.by
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val item = items!![position]
        holder.bind(item)

        if (position == items!!.size - 1
            && !loading) {
            listener?.let {
                loading = true
                it.onLoadMore()
            }
        }
    }

    interface PostsAdapterListener {
        fun onLoadMore()
    }

}