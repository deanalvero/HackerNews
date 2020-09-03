package com.lowbottgames.reader.hackernews.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.lowbottgames.reader.hackernews.model.HNItem
import com.lowbottgames.reader.hackernews.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(
    application: Application,
    private val repository: PostRepository
) : AndroidViewModel(application) {

    companion object {
        const val ITEMS_PER_CALL = 20
    }

    private var storyIds: List<Long>? = null
    private var storyIdIndex = 0

    private val _itemsList = ArrayList<HNItem>()
    private val _items = MutableLiveData<List<HNItem>>()
    val items: LiveData<List<HNItem>>
        get() = _items

    init {
        topStories(false)
    }

    fun topStories(isRefresh: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        val storyIds = this@PostViewModel.storyIds ?: repository.topStories(isRefresh)

        val endIndex = storyIds.size.coerceAtMost(storyIdIndex + ITEMS_PER_CALL)
        if (storyIdIndex < endIndex) {
            storyIds.subList(storyIdIndex, endIndex).map { id ->
                val item = repository.item(isRefresh, id)
                _itemsList.add(item)
            }
            _items.postValue(_itemsList)
            storyIdIndex = endIndex
        }
    }

}