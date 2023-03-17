package com.rijaldev.mygithub.presentation.detail.repo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rijaldev.mygithub.data.remote.Result
import com.rijaldev.mygithub.databinding.FragmentRepoBinding
import com.rijaldev.mygithub.domain.model.Repo
import com.rijaldev.mygithub.presentation.adapter.RepoAdapter
import com.rijaldev.mygithub.presentation.adapter.SectionPagerAdapter
import com.rijaldev.mygithub.vm.ViewModelFactory

class RepoFragment : Fragment() {

    private var _binding: FragmentRepoBinding? = null
    private val binding get() = _binding
    private lateinit var repoAdapter: RepoAdapter
    private val viewModel: RepoViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    private val username by lazy {
        arguments?.getString(SectionPagerAdapter.EXTRA_USERNAME)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState === null) {
            viewModel.setUsername(username.toString())
        }

        setUpRecyclerView()
        setUpView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {
        repoAdapter = RepoAdapter(
            onItemClicked = {}
        )
        binding?.rvRepo?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = repoAdapter
        }
    }

    private fun setUpView() {
        viewModel.repos.observe(viewLifecycleOwner, repoObserver)
        binding?.contentError?.btnRetry?.setOnClickListener {
            viewModel.setUsername(username.toString())
        }
    }

    private val repoObserver = Observer<Result<List<Repo>>> { result ->
        when (result) {
            is Result.Loading -> {
                setState(
                    isError = false,
                    isLoading = true
                )
            }
            is Result.Success -> {
                val repos = result.data
                setState(
                    isError = false,
                    isLoading = false,
                    isEmpty = repos.isNullOrEmpty()
                )
                repoAdapter.submitList(repos)
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
            shimmerFrameRepo.root.isVisible = isLoading
            rvRepo.isVisible = !isError and !isLoading
        }
    }
}