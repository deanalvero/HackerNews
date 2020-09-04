package com.lowbottgames.reader.hackernews.model

data class HNItem(
    val id: Long,
    val by: String,
    val descendants: Int,
    val kids: List<Long>,
    val score: Int,
    val time: Long,
    val title: String,
    val type: String,
    val url: String,

    val parent: Long,
    val text: String?,
    val poll: Long
) {

}