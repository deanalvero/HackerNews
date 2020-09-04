package com.lowbottgames.reader.hackernews.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class HNPostEntity(
    @PrimaryKey
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