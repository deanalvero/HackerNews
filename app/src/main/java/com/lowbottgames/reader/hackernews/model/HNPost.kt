package com.lowbottgames.reader.hackernews.model

data class HNPost(
    val id: Long,
    val deleted: Boolean?,
    val type: String?,
    val by: String?,
    val time: Long?,
    val text: String?,
    val dead: Boolean?,
    val parent: Long?,
    val poll: Long?,
    val kids: List<Long>?,
    val url: String?,
    val score: Int?,
    val title: String?,
    val parts: List<Long>?,
    val descendants: Int?
)