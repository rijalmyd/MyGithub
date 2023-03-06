package com.rijaldev.mygithub.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rijaldev.mygithub.databinding.ItemRepoBinding
import com.rijaldev.mygithub.domain.model.Repo
import com.rijaldev.mygithub.util.ChipLoader.addChip
import com.rijaldev.mygithub.util.DateFormatter.getTimeAgo
import com.rijaldev.mygithub.util.LanguageColor.setLeftDrawableColor
import com.rijaldev.mygithub.util.NumberFormatter.shortenNumber

class RepoAdapter(
    private val onItemClicked: () -> Unit,
) : ListAdapter<Repo, RepoAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoAdapter.ViewHolder {
        val binding = ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoAdapter.ViewHolder, position: Int) {
        val repo = getItem(position)
        holder.bind(repo)
    }

    inner class ViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            binding.apply {
                tvRepoLanguage.setLeftDrawableColor(itemView.context, repo.language)
                tvRepoName.text = repo.name
                tvUpdatedAt.text = repo.updatedAt?.getTimeAgo()
                tvRepoDesc.text = repo.description
                tvStarsCount.text = repo.stargazersCount.shortenNumber()
                tvForksCount.text = repo.forksCount.shortenNumber()
                tvRepoLanguage.text = repo.language ?: "Unknown"
                cgTopics.removeAllViews()
                repo.topics?.take(3)?.forEach { label ->
                    cgTopics.addChip(itemView.context, label.toString())
                }
            }
            itemView.setOnClickListener {
                onItemClicked()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo) =
                oldItem == newItem
        }
    }
}