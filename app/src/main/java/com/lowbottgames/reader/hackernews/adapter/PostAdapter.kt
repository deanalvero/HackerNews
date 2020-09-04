package com.lowbottgames.reader.hackernews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.lowbottgames.reader.hackernews.R
import com.lowbottgames.reader.hackernews.model.HNItem

class PostAdapter() : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    var items: List<HNItem>? = null
    var listener: PostAdapterListener? = null

    var loading = false

    class PostViewHolder(itemView: View, onClickListener: OnClickListener?) : RecyclerView.ViewHolder(itemView) {
        private val textViewText: TextView  = itemView.findViewById(R.id.textView_text)
        private val textViewSubtext: TextView  = itemView.findViewById(R.id.textView_subtext)

        init {
            itemView.setOnClickListener {
                onClickListener?.onClick(adapterPosition)
            }
        }

        fun bind(item: HNItem) {
            textViewText.text = item.text?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }
            textViewSubtext.text = item.by
        }

        interface OnClickListener {
            fun onClick(position: Int)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return PostViewHolder(view, object : PostViewHolder.OnClickListener {
            override fun onClick(position: Int) {
            }
        })
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
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

    interface PostAdapterListener {
        fun onLoadMore()
    }

}