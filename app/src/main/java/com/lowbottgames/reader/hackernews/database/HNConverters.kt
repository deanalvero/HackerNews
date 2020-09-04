package com.lowbottgames.reader.hackernews.database

import androidx.core.text.isDigitsOnly
import androidx.room.TypeConverter

class HNConverters {

    @TypeConverter
    fun fromString(value: String?): List<Long?> {
        if (value == null) {
            return listOf<Long>()
        }

        return value.split(" ").filter {
            it.isNotEmpty() && it.isDigitsOnly()
        }.map {
            it.toLong()
        }
    }

    @TypeConverter
    fun longListToString(list: List<Long>?) : String {
        if (list == null) {
            return ""
        }
        return list.toString()
            .replace(",", " ")
            .replace("[\\[\\]]".toRegex(), "")
    }

}