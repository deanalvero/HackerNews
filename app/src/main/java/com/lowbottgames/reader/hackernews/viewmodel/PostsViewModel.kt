package com.lowbottgames.reader.hackernews.viewmodel

import androidx.lifecycle.*
import com.lowbottgames.reader.hackernews.model.HNPost
import com.lowbottgames.reader.hackernews.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostsViewModel(
    private val repository: PostRepository
) : ViewModel() {

    companion object {
        const val ITEMS_PER_CALL = 20
    }

    private var storyIds: List<Long>? = null
    private var storyIdIndex = 0

    private val _itemsList = ArrayList<HNPost>()
    private val _items = MutableLiveData<List<HNPost>>()
    val items: LiveData<List<HNPost>>
        get() = _items

    init {
        topPosts(false)
    }

    fun topPosts(isRefresh: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        val storyIds = this@PostsViewModel.storyIds ?: repository.topPosts(isRefresh)

        if (storyIds != null) {
            val endIndex = storyIds.size.coerceAtMost(storyIdIndex + ITEMS_PER_CALL)
            if (storyIdIndex < endIndex) {
                storyIds.subList(storyIdIndex, endIndex).map { id ->
                    val item = repository.item(isRefresh, id)
                    if (item != null) {
                        _itemsList.add(item)
                    }
                }
                _items.postValue(_itemsList)
                storyIdIndex = endIndex
            }
        }
    }

}