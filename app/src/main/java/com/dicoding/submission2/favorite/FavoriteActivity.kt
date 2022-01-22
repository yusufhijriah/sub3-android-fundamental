package com.dicoding.submission2.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.DetailUserActivity
import com.dicoding.submission2.User
import com.dicoding.submission2.adapter.listUserAdapter
import com.dicoding.submission2.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private var adapter = listUserAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = FavoriteViewModel(application)

        val actionbar = supportActionBar
        actionbar!!.title = "Favorite"

        setupAdapter()

        viewModel.getFavoriteUser()?.observe(this, {
            val data = it.map { favUser ->
                User(
                    id = favUser.id,
                    username = favUser.username,
                    avatar = favUser.avatar,
                    company = "",
                    location = "",
                    name = favUser.username,
                    repository = favUser.repository,
                    followers = favUser.followers,
                    following = favUser.following
                )
            }
            adapter.setFilterUser(data)
            binding.emptyFavList.isVisible = data.isNullOrEmpty()
        })
    }

    private fun setupAdapter() {
        with(binding.rvFavUser) {
            this@FavoriteActivity.adapter.setOnItemClickCallback(object :
                listUserAdapter.OnItemClickCallback {
                override fun onItemClicked(user: User) {
                    startActivity(
                        Intent(this@FavoriteActivity, DetailUserActivity::class.java).putExtra(
                            DetailUserActivity.EXTRA_USER,
                            user
                        )
                    )
                }

            })
            layoutManager =
                LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL, false)
            adapter = this@FavoriteActivity.adapter
        }
    }
}