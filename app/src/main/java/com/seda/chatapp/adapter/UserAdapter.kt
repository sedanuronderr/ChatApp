package com.seda.chatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seda.chatapp.R
import com.seda.chatapp.databinding.ItemUserBinding
import com.seda.chatapp.model.User

class UserAdapter(private val context: Context,private val userList:ArrayList<User>):RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(val binding:ItemUserBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
return  ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var user = userList[position]
        holder.binding.userrname.text = user.username
        Glide.with(context).load(user.profileImage).placeholder(R.drawable.acount)
            .into(holder.binding.userImage)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}