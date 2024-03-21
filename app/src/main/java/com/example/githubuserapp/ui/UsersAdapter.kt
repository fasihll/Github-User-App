package com.example.githubuserapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.databinding.ItemUsersBinding
import com.example.core.data.remote.response.ItemsItem
import com.example.githubuserapp.detail.DetailUserActivity

class UsersAdapter: ListAdapter<ItemsItem, UsersAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUsersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)
    }

    class MyViewHolder(private val binding: ItemUsersBinding): RecyclerView.ViewHolder(binding
        .root) {

        fun bind(users: ItemsItem){
            Glide.with(itemView.context)
                .load(users.avatarUrl)
                .into(binding.profileImage)

            binding.tvUsername.text = users.login
            binding.tvUrl.text = users.htmlUrl

            itemView.setOnClickListener{
                    val intent = Intent(itemView.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.USERNAME,users.login)
                    intent.putExtra(DetailUserActivity.AVATAR_URL,users.avatarUrl)
                    intent.putExtra(DetailUserActivity.GITHUB_URL,users.htmlUrl)
                    itemView.context.startActivity(intent)
            }
        }
    }

    companion object{
        val DIFF_CALLBACK= object : DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return  oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
               return oldItem == newItem
            }
        }
    }
}