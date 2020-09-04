package com.lowbottgames.reader.hackernews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lowbottgames.reader.hackernews.repository.PostRepository
import java.lang.IllegalArgumentException

class PostsViewModelFactory(
    private val repository: PostRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }
}