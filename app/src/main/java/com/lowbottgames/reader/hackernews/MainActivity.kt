package com.lowbottgames.reader.hackernews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lowbottgames.reader.hackernews.model.HNItem

class MainActivity : AppCompatActivity(),
    PostsFragment.OnPostsEventsListener
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = PostsFragment()
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, fragment)
                .commit()
        }
    }

    override fun onPostClick(item: HNItem) {
        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, PostFragment.newInstance(item))
            .addToBackStack(PostFragment::class.simpleName)
            .commit()
    }
}