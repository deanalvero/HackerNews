package com.lowbottgames.reader.hackernews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lowbottgames.reader.hackernews.adapter.PostsAdapter
import com.lowbottgames.reader.hackernews.model.HNItem
import com.lowbottgames.reader.hackernews.network.HackerNewsApiEndpoint
import com.lowbottgames.reader.hackernews.network.ServiceBuilder
import com.lowbottgames.reader.hackernews.repository.PostRepository
import com.lowbottgames.reader.hackernews.viewmodel.PostsViewModel
import com.lowbottgames.reader.hackernews.viewmodel.PostsViewModelFactory

class PostsFragment : Fragment() {

    private val TAG = PostsFragment::class.java.simpleName

    private val service = ServiceBuilder.buildService(HackerNewsApiEndpoint::class.java)
    private val repository = PostRepository(service)

    private lateinit var postsViewModel: PostsViewModel
    private lateinit var listener: OnPostsEventsListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsViewModel = ViewModelProvider(
            this,
            PostsViewModelFactory(repository)
        ).get(PostsViewModel::class.java)

        val adapter = PostsAdapter()
        adapter.listener = object : PostsAdapter.PostsAdapterListener {
            override fun onItemClick(item: HNItem) {
                listener.onPostClick(item)
            }

            override fun onLoadMore() {
                postsViewModel.topStories(false)
            }
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        postsViewModel.items.observe(this) {
            adapter.items = it
            adapter.notifyDataSetChanged()
            adapter.loading = false
        }

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.app_name)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnPostsEventsListener
    }

    interface OnPostsEventsListener {
        fun onPostClick(item: HNItem)
    }
}