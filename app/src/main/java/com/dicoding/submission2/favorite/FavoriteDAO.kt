package com.dicoding.submission2.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavorite(favoriteUser: Favorite)

    @Query("SELECT * FROM favorite")
    fun getFavoriteUser(): LiveData<List<Favorite>>

    @Query("SELECT COUNT(*) FROM favorite WHERE favorite.id = :id")
    fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    fun removeFromFavorite(id: Int): Int
}