package com.lowbottgames.reader.hackernews.repository

import com.lowbottgames.reader.hackernews.model.HNItem
import com.lowbottgames.reader.hackernews.network.HackerNewsApiEndpoint

class PostRepository(private val service: HackerNewsApiEndpoint) {
    suspend fun topStories(isRefresh: Boolean): List<Long> {
        return service.topStories()
    }

    suspend fun item(isRefresh: Boolean, id: Long): HNItem {
        return service.item(id)
    }
}