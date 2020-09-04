package com.lowbottgames.reader.hackernews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [HNCategoryEntity::class, HNPostEntity::class], version = 1, exportSchema = false)
@TypeConverters(HNConverters::class)
abstract class HNDatabase : RoomDatabase() {

    abstract val databaseDao: HNDatabaseDao

    companion object {

        @Volatile
        private lateinit var INSTANCE: HNDatabase

        fun getInstance(context: Context): HNDatabase {
            synchronized(HNDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        HNDatabase::class.java,
                        "hnr_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}