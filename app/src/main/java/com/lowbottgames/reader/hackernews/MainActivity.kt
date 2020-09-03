package com.lowbottgames.reader.hackernews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lowbottgames.reader.hackernews.adapter.PostsAdapter
import com.lowbottgames.reader.hackernews.network.HackerNewsApiEndpoint
import com.lowbottgames.reader.hackernews.network.ServiceBuilder
import com.lowbottgames.reader.hackernews.repository.PostRepository
import com.lowbottgames.reader.hackernews.viewmodel.PostViewModel
import com.lowbottgames.reader.hackernews.viewmodel.PostViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = PostsAdapter()
        adapter.listener = object : PostsAdapter.PostsAdapterListener {
            override fun onLoadMore() {
                postViewModel.topStories(false)
            }
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val service = ServiceBuilder.buildService(HackerNewsApiEndpoint::class.java)
        val repository = PostRepository(service)

        postViewModel = ViewModelProvider(
            this,
            PostViewModelFactory(application, repository)
        ).get(PostViewModel::class.java)

        postViewModel.items.observe(this) {
            adapter.items = it
            adapter.notifyDataSetChanged()
            adapter.loading = false
        }
    }
}