package com.rijaldev.mygithub.ui.main.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.databinding.FragmentHomeBinding
import com.rijaldev.mygithub.domain.model.User
import com.rijaldev.mygithub.ui.adapter.UserAdapter
import com.rijaldev.mygithub.vm.ViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance()
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
                onItemClicked = { username ->
                    val toDetailUser = HomeFragmentDirections.actionHomeFragmentToDetailActivity(username)
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
        viewModel.users.observe(viewLifecycleOwner) { users ->
            userAdapter.submitList(users)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
            showError(isError = false, isLoading = isLoading)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let {
                showError(true)
            }
        }
        binding?.searchView?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrBlank()) {
                        viewModel.searchUsers(query)
                    }
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    if (!query.isNullOrBlank()) {
                        viewModel.searchUsers(query)
                        Log.d("HomeFragment", "onQueryTextChange: $query")
                    }
                    return true
                }
            })
        }
        binding?.contentError?.btnRetry?.setOnClickListener {
            val query = binding?.searchView?.query
            if (!query.isNullOrBlank()) viewModel.retry(true)
            else viewModel.retry(false)
        }
    }

    private fun showError(isError: Boolean, isLoading: Boolean = false) {
        binding?.apply {
            contentError.root.isVisible = isError and !isLoading
            rvUser.isVisible = !isError and !isLoading
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.apply {
            shimmerFrameUser.root.isVisible = isLoading
        }
    }
}