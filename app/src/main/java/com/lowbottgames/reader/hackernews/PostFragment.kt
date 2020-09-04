package com.lowbottgames.reader.hackernews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lowbottgames.reader.hackernews.adapter.PostAdapter
import com.lowbottgames.reader.hackernews.model.HNItem
import com.lowbottgames.reader.hackernews.network.HackerNewsApiEndpoint
import com.lowbottgames.reader.hackernews.network.ServiceBuilder
import com.lowbottgames.reader.hackernews.repository.PostRepository
import com.lowbottgames.reader.hackernews.viewmodel.PostViewModel
import com.lowbottgames.reader.hackernews.viewmodel.PostViewModelFactory

class PostFragment : Fragment() {

    private val service = ServiceBuilder.buildService(HackerNewsApiEndpoint::class.java)
    private val repository = PostRepository(service)

    private lateinit var postViewModel: PostViewModel
    private val id: Long by lazy {
        arguments?.getLong(KEY_ID, 0) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewTitle: TextView = view.findViewById(R.id.textView_title)
        val textViewSubtitle: TextView = view.findViewById(R.id.textView_subtitle)
        val textViewText: TextView = view.findViewById(R.id.textView_text)

        postViewModel = ViewModelProvider(
            this,
            PostViewModelFactory(repository)
        ).get(PostViewModel::class.java)

        val adapter = PostAdapter()
        adapter.listener = object : PostAdapter.PostAdapterListener {
            override fun onLoadMore() {
                postViewModel.loadComments(false)
            }
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter


        postViewModel.post.observe(this) {
            postViewModel.loadComments(false)

            textViewTitle.text = it.title
            textViewSubtitle.text = it.by

            with(textViewText) {
                if (it.text == null) {
                    visibility = View.GONE
                } else {
                    visibility = View.VISIBLE
                    text = it.text
                }
            }
        }

        postViewModel.comments.observe(this) {
            adapter.items = it
            adapter.notifyDataSetChanged()
            adapter.loading = false
        }

        postViewModel.loadPost(false, id)


        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.app_name)
        toolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }

    companion object {
        const val KEY_ID = "KEY_ID"
        fun newInstance(item: HNItem) : Fragment {
            val fragment = PostFragment()
            val arguments = Bundle()
            arguments.putLong(KEY_ID, item.id)
            fragment.arguments = arguments
            return fragment
        }
    }

}