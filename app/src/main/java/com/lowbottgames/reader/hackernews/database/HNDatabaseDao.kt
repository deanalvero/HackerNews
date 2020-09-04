package com.lowbottgames.reader.hackernews.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HNDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: HNCategoryEntity)

    @Query("SELECT * FROM category_table WHERE type = :type LIMIT 1")
    fun getCategory(type: Int) : HNCategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: HNPostEntity)

    @Query("SELECT * FROM post_table WHERE id = :id LIMIT 1")
    fun getItem(id: Long) : HNPostEntity?

}