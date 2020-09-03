package com.lowbottgames.reader.hackernews.network

import com.lowbottgames.reader.hackernews.model.HNItem
import retrofit2.http.GET
import retrofit2.http.Path

interface HackerNewsApiEndpoint {
    @GET("/v0/topstories.json")
    suspend fun topStories() : List<Long>
    @GET("/v0/item/{id}.json")
    suspend fun item(@Path("id") id: Long) : HNItem
}