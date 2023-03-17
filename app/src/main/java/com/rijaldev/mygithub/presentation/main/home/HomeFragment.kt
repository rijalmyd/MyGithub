package com.rijaldev.mygithub.presentation.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.databinding.FragmentHomeBinding
import com.rijaldev.mygithub.domain.model.User
import com.rijaldev.mygithub.presentation.adapter.UserAdapter
import com.rijaldev.mygithub.vm.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {
        binding?.apply {
            userAdapter = UserAdapter(
                onItemClicked = { user ->
                    val toDetailUser = HomeFragmentDirections.actionHomeFragmentToDetailActivity()
                    toDetailUser.user = user
                    findNavController().navigate(toDetailUser)
                }
            )
            with(rvUser) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = userAdapter
            }
        }
    }

    private fun setUpView() {
        viewModel.searchUsers.observe(viewLifecycleOwner, userObserver)
        binding?.searchView?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrBlank()) {
                        viewModel.fetchSearchUsers(query)
                    }
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(query: String?) = false
            })
        }
        binding?.contentError?.btnRetry?.setOnClickListener {
            val query = binding?.searchView?.query
            if (!query.isNullOrBlank()) viewModel.fetchSearchUsers("$query")
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
            searchAnim.isVisible = false
            contentError.root.isVisible = isError
            contentEmpty.root.isVisible = isEmpty
            shimmerFrameUser.root.isVisible = isLoading
            rvUser.isVisible = !isError and !isLoading
        }
    }
}