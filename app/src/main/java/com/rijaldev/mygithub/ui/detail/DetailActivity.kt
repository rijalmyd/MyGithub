package com.rijaldev.mygithub.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.rijaldev.mygithub.R
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.databinding.ActivityDetailBinding
import com.rijaldev.mygithub.domain.model.DetailUser
import com.rijaldev.mygithub.ui.adapter.SectionPagerAdapter
import com.rijaldev.mygithub.util.ColorType.setColor
import com.rijaldev.mygithub.util.NumberFormatter.shortenNumber
import com.rijaldev.mygithub.vm.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val navArgs: DetailActivityArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            viewModel.setUsername(navArgs.username.toString())
        }

        setUpToolbar()
        setUpViewPager()
        setUpView()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun setUpToolbar() {
        with(binding) {
            toolbar.title = navArgs.username
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setUpViewPager() {
        val sectionPagerAdapter = SectionPagerAdapter(
            activity = this,
            username = navArgs.username.toString()
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
            viewModel.setUsername(navArgs.username.toString())
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