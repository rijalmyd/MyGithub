package com.rijaldev.mygithub.presentation.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rijaldev.mygithub.databinding.FragmentFavoriteBinding
import com.rijaldev.mygithub.presentation.adapter.UserAdapter
import com.rijaldev.mygithub.vm.ViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var userAdapter: UserAdapter
    private val viewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
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
                    val toDetailUser = FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity()
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
        viewModel.favoriteUsers.observe(viewLifecycleOwner) { users ->
            setState(users.isNullOrEmpty())
            userAdapter.submitList(users)
        }
    }

    private fun setState(isEmpty: Boolean) {
        binding?.apply {
            contentEmpty.root.isVisible = isEmpty
            rvUser.isVisible = !isEmpty
        }
    }
}