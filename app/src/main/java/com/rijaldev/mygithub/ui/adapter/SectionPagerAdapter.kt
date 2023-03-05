package com.rijaldev.mygithub.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rijaldev.mygithub.ui.detail.DetailActivity
import com.rijaldev.mygithub.ui.detail.follow.FollowFragment
import com.rijaldev.mygithub.ui.detail.repo.RepoFragment
import com.rijaldev.mygithub.util.BundleDSL

class SectionPagerAdapter(
    activity: AppCompatActivity,
    private val username: String,
) : FragmentStateAdapter(activity) {

    override fun getItemCount() = DetailActivity.TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = RepoFragment()
            1, 2 -> fragment = FollowFragment()
        }
        val bundle = Bundle().apply {
            putInt(FollowFragment.EXTRA_POSITION, position)
            putString(FollowFragment.EXTRA_USERNAME, username)
        }
        fragment?.arguments = bundle
        return fragment as Fragment
    }
}