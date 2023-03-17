package com.rijaldev.mygithub.presentation.detail.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.databinding.FragmentFollowBinding
import com.rijaldev.mygithub.domain.model.User
import com.rijaldev.mygithub.presentation.adapter.SectionPagerAdapter
import com.rijaldev.mygithub.presentation.adapter.UserAdapter
import com.rijaldev.mygithub.vm.ViewModelFactory

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: FollowViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    private val username by lazy {
        arguments?.getString(SectionPagerAdapter.EXTRA_USERNAME)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState === null) {
            viewModel.setUsername(username.toString())
        }

        setUpType()
        setUpRecyclerView()
        setUpView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {
        userAdapter = UserAdapter(
            onItemClicked = {}
        )
        binding?.rvUser?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }

    private fun setUpView() {
        binding?.contentError?.btnRetry?.setOnClickListener {
            viewModel.setUsername(username.toString())
            setUpType()
        }
    }

    private fun setUpType() {
        when (arguments?.getInt(SectionPagerAdapter.EXTRA_POSITION, 1)) {
            1 -> viewModel.userFollowers.observe(viewLifecycleOwner, userObserver)
            2 -> viewModel.userFollowing.observe(viewLifecycleOwner, userObserver)
        }
    }

    private val userObserver = Observer<Result<List<User>>> { result ->
        when (result) {
            is Result.Loading -> {
                setState(
                    isError = false,
                    isLoading = true
                )
            }

            is Result.Success -> {
                val users = result.data
                setState(
                    isError = false,
                    isLoading = false,
                    isEmpty = users.isNullOrEmpty()
                )
                userAdapter.submitList(users)
            }

            is Result.Error -> {
                setState(
                    isError = true,
                    isLoading = false
                )
            }
        }
    }

    private fun setState(isError: Boolean, isLoading: Boolean, isEmpty: Boolean = false) {
        binding?.apply {
            contentError.root.isVisible = isError
            contentEmpty.root.isVisible = isEmpty
            shimmerFrameUser.root.isVisible = isLoading
            rvUser.isVisible = !isError and !isLoading
        }
    }
}