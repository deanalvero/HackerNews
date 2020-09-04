package com.lowbottgames.reader.hackernews.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class HNCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val type: Int,

    val items: List<Long>
)