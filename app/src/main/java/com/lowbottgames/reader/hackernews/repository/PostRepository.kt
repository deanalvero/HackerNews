package com.lowbottgames.reader.hackernews.repository

import com.lowbottgames.reader.hackernews.database.HNDatabase
import com.lowbottgames.reader.hackernews.database.HNCategoryEntity
import com.lowbottgames.reader.hackernews.model.HNPost
import com.lowbottgames.reader.hackernews.network.HackerNewsApiEndpoint
import java.lang.Exception

class PostRepository(
    private val database: HNDatabase,
    private val service: HackerNewsApiEndpoint
) {

    suspend fun topPosts(isRefresh: Boolean): List<Long>? {
        var category = database.databaseDao.getCategory(1)
        if (category == null || isRefresh) {
            val postIds= try {
                service.topPosts()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            if (postIds != null) {
                category = HNCategoryEntity(
                    0,
                    1,
                    postIds
                )
                database.databaseDao.insert(category)
            }
        }
        return category?.items
    }

    suspend fun item(isRefresh: Boolean, id: Long): HNPost? {
        var post = database.databaseDao.getItem(id)
        if (post == null || isRefresh) {
            post = try {
                service.item(id)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            if (post != null) {
                database.databaseDao.insert(post)
            }
        }

        post?.let {
            return HNPost(
                it.id,
                it.deleted,
                it.type,
                it.by,
                it.time,
                it.text,
                it.dead,
                it.parent,
                it.poll,
                it.kids,
                it.url,
                it.score,
                it.title,
                it.parts,
                it.descendants
            )
        }
        return null
    }
}