package com.rijaldev.mygithub.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rijaldev.mygithub.databinding.ItemUserBinding
import com.rijaldev.mygithub.domain.model.User
import com.rijaldev.mygithub.util.ColorType.setColor

class UserAdapter(
    private val onItemClicked: (String?) -> Unit,
) : ListAdapter<User, UserAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(ivUser)
                tvUsername.text = user.username
                tvUserType.setColor(itemView.context, user.type)
            }
            itemView.setOnClickListener { onItemClicked(user.username) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) =
                oldItem.username == newItem.username

            override fun areContentsTheSame(oldItem: User, newItem: User) =
                oldItem == newItem
        }
    }
}