package com.dicoding.submission2.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: FavoriteDAO? = null
    private var userDb: GithubDatabase? = null

    init {
        userDb = GithubDatabase.getDataBase(application)
        userDao = userDb?.favorite()
    }

    fun getFavoriteUser(): LiveData<List<Favorite>>? {
        return userDao?.getFavoriteUser()
    }
}