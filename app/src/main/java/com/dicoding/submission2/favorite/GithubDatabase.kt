package com.dicoding.submission2.favorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Favorite::class],
    version = 1
)

abstract class GithubDatabase : RoomDatabase() {
    companion object {
        var INSTANCE: GithubDatabase? = null

        fun getDataBase(context: Context): GithubDatabase? {
            if (INSTANCE == null) {
                synchronized(GithubDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GithubDatabase::class.java,
                        "user_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favorite(): FavoriteDAO
}