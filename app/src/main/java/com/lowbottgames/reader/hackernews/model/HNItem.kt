package com.lowbottgames.reader.hackernews.model

data class HNItem(
    val id: Long,
    val by: String,
    val descendants: Int,
    val kids: Array<Long>,
    val score: Int,
    val time: Long,
    val title: String,
    val type: String,
    val url: String,

    val parent: Long,
    val text: String,
    val poll: Long
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HNItem

        if (id != other.id) return false
        if (by != other.by) return false
        if (descendants != other.descendants) return false
        if (!kids.contentEquals(other.kids)) return false
        if (score != other.score) return false
        if (time != other.time) return false
        if (title != other.title) return false
        if (type != other.type) return false
        if (url != other.url) return false
        if (parent != other.parent) return false
        if (text != other.text) return false
        if (poll != other.poll) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + by.hashCode()
        result = 31 * result + descendants
        result = 31 * result + kids.contentHashCode()
        result = 31 * result + score
        result = 31 * result + time.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + parent.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + poll.hashCode()
        return result
    }
}