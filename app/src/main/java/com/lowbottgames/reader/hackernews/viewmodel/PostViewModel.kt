package com.lowbottgames.reader.hackernews.viewmodel

import androidx.lifecycle.*
import com.lowbottgames.reader.hackernews.model.HNItem
import com.lowbottgames.reader.hackernews.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(
    private val repository: PostRepository
) : ViewModel() {

    companion object {
        const val ITEMS_PER_CALL = 20
    }

    private val _post = MutableLiveData<HNItem>()
    val post: LiveData<HNItem>
        get() = _post


    private var commentIdIndex = 0

    private val _commentsList = ArrayList<HNItem>()
    private val _comments = MutableLiveData<List<HNItem>>()
    val comments: LiveData<List<HNItem>>
        get() = _comments

    init {
        loadComments(false)
    }

    fun loadPost(isRefresh: Boolean, id: Long) = viewModelScope.launch(Dispatchers.IO) {
        val item = repository.item(isRefresh, id)
        _post.postValue(item)
    }

    fun loadComments(isRefresh: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        val postIds = this@PostViewModel._post.value?.kids

        if (postIds != null) {
            val endIndex = postIds.size.coerceAtMost(commentIdIndex + PostViewModel.ITEMS_PER_CALL)
            if (commentIdIndex < endIndex) {
                postIds.subList(commentIdIndex, endIndex).map { id ->
                    val item = repository.item(isRefresh, id)
                    _commentsList.add(item)
                }
                _comments.postValue(_commentsList)
                commentIdIndex = endIndex
            }
        }
    }
}