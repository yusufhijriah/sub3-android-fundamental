package com.dicoding.submission2

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.submission2.favorite.Favorite
import com.dicoding.submission2.favorite.FavoriteDAO
import com.dicoding.submission2.favorite.GithubDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : ViewModel() {
    private var userDao: FavoriteDAO? = null
    private var userDb: GithubDatabase? = null

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    init {
        userDb = GithubDatabase.getDataBase(application)
        userDao = userDb?.favorite()
    }

    fun insert(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isFavorite.value != null && isFavorite.value == true) {
                userDao?.removeFromFavorite(favorite.id)
            } else {
                userDao?.addToFavorite(favorite)
            }
            isFavorite(favorite.id)
        }
    }

    fun isFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = userDao?.checkUser(id)
            if (result != null) {
                _isFavorite.postValue(result > 0)
            }
        }
    }
}