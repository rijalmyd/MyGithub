package com.rijaldev.mygithub.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.rijaldev.mygithub.R
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.databinding.ActivityDetailBinding
import com.rijaldev.mygithub.domain.model.DetailUser
import com.rijaldev.mygithub.presentation.adapter.SectionPagerAdapter
import com.rijaldev.mygithub.util.ColorType.setColor
import com.rijaldev.mygithub.util.NumberFormatter.shortenNumber
import com.rijaldev.mygithub.vm.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var menu: Menu? = null
    private var isFavoriteUser = false
    private val navArgs: DetailActivityArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState === null) {
            viewModel.setUsername(navArgs.user?.username.toString())
        }

        setUpToolbar()
        setUpViewPager()
        setUpView()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_detail, menu)
        viewModel.isFavoriteUser(navArgs.user?.username.toString()).observe(this) { isFavoriteUser ->
            this.isFavoriteUser = isFavoriteUser
            setFavoriteState(isFavoriteUser)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_favorite -> onFavButtonClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpToolbar() {
        with(binding) {
            toolbar.title = navArgs.user?.username
            setSupportActionBar(toolbar)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpViewPager() {
        val sectionPagerAdapter = SectionPagerAdapter(
            activity = this,
            username = navArgs.user?.username.toString()
        )
        with(binding) {
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    private fun setUpView() {
        binding.btnRetry.setOnClickListener {
            viewModel.setUsername(navArgs.user?.username.toString())
        }
        viewModel.detailUser.observe(this, userObserver)
    }

    private val userObserver = Observer<Result<DetailUser>> { result ->
        when (result) {
            is Result.Loading -> {
                setState(
                    isError = false,
                    isLoading = true
                )
            }
            is Result.Success -> {
                setState(
                    isError = false,
                    isLoading = false
                )
                val user = result.data
                user?.populateDetailUser()
            }
            is Result.Error -> {
                setState(
                    isError = true,
                    isLoading = false
                )
            }
        }
    }

    private fun DetailUser.populateDetailUser() {
        with(binding.contentDetailUser) {
            Glide.with(this@DetailActivity)
                .load(avatarUrl)
                .into(ivUser)
            tvUserType.setColor(this@DetailActivity, type)
            tvFollowersCount.text = followers.shortenNumber()
            tvReposCount.text = publicRepos.shortenNumber()
            tvFollowingCount.text = following.shortenNumber()
            tvFullName.text = name
            tvUserBio.text = bio
            tvUserBio.isVisible = !bio.isNullOrBlank()
        }
    }

    private fun onFavButtonClicked() {
        setFavoriteState(!isFavoriteUser)
        if (isFavoriteUser) {
            navArgs.user?.let { viewModel.delete(it.username.toString()) }
        } else {
            navArgs.user?.let { viewModel.insert(it) }
        }
    }

    private fun setFavoriteState(isFavoriteUser: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.btn_favorite)
        menuItem?.apply {
            icon = ContextCompat.getDrawable(
                this@DetailActivity,
                if (isFavoriteUser) R.drawable.ic_favorite_fill
                else R.drawable.ic_favorite
            )
        }
    }

    private fun setState(isError: Boolean, isLoading: Boolean) {
        binding.apply {
            btnRetry.isVisible = isError
            shimmerFrameUser.root.isVisible = isLoading
            contentDetailUser.root.visibility = if (!isLoading and !isError) View.VISIBLE else View.INVISIBLE
        }
    }

    companion object {
        @StringRes
        val TAB_TITLES = arrayOf(R.string.repos, R.string.followers, R.string.following)
    }
}