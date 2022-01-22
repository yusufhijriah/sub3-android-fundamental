package com.dicoding.submission2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.submission2.api.ApiConfig
import com.dicoding.submission2.api.UserResponseItem
import com.dicoding.submission2.databinding.ActivityDetailUserBinding
import com.dicoding.submission2.favorite.Favorite
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        val actionBar = supportActionBar
        actionBar?.title = user?.username.toString()
        val username = user?.username
        findUsers(username.toString())
        viewModel = DetailViewModel(application)

        supportActionBar?.elevation = 0f
        if (user != null) setupListener(user)


        val fragment = FollowingAndFollowerFragment()
        val bundle = Bundle()
        bundle.putString(FollowingAndFollowerFragment.EXTRA_USERNAME, username)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        viewModel.isFavorite.observe(this, this::isFavorite)
        user?.id?.let { viewModel.isFavorite(it) }
    }

    private fun isFavorite(isFavorite: Boolean) {
        binding.buttonFavorite.isChecked = isFavorite
    }

    private fun setupListener(user: User) {
        binding.buttonFavorite.setOnClickListener {
            val favorite = Favorite(
                id = user.id,
                username = user.username,
                avatar = user.avatar,
                company = user.company,
                location = user.location,
                name = user.name,
                repository = user.repository,
                followers = user.followers,
                following = user.following

            )
            viewModel.insert(favorite)
        }
    }

    private fun findUsers(username: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserResponseItem> {
            override fun onResponse(
                call: Call<UserResponseItem>,
                response: Response<UserResponseItem>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    setData(responseBody)
                    showLoading(false)
                }
            }

            override fun onFailure(call: Call<UserResponseItem>, t: Throwable) {
                showLoading(false)
            }
        }
        )
    }

    private fun setData(dataUser: UserResponseItem) {
        binding.tvUsername.text = dataUser.login

        Glide.with(this)
            .load(dataUser.avatarUrl)
            .apply(RequestOptions())
            .into(binding.imageView)

        binding.tvCompany.text = dataUser.company
        binding.tvLocation.text = dataUser.location
        binding.tvName.text = dataUser.name
        binding.tvRepository.text = dataUser.publicRepos.toString()
        binding.tvFollowers.text = dataUser.followers.toString()
        binding.tvFollowing.text = dataUser.following.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}