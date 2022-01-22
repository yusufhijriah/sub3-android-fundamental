package com.dicoding.submission2.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey
    val id: Int,
    var username: String,
    var avatar: String,
    var company: String,
    var location: String,
    var name: String,
    var repository: String,
    var followers: String,
    var following: String,
) : Serializable