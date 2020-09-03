package com.lowbottgames.reader.hackernews.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lowbottgames.reader.hackernews.repository.PostRepository
import java.lang.IllegalArgumentException

class PostViewModelFactory(
    private val application: Application,
    private val repository: PostRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unable to construct ViewModel")
    }
}