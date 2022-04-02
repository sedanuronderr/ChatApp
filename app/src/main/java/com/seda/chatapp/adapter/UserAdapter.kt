package com.seda.chatapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seda.chatapp.R
import com.seda.chatapp.activity.ChatActivity
import com.seda.chatapp.databinding.ItemUserBinding
import com.seda.chatapp.model.User

class UserAdapter(private val context: Context,private val userList:ArrayList<User>):RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    class ViewHolder(val binding:ItemUserBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
return  ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]

        holder.binding.userrname.text = user.username
        Glide.with(context).load(user.profileImage).placeholder(R.drawable.acount)
            .into(holder.binding.userImage)

        holder.binding.card.apply {
          setOnClickListener {
              holder.binding.apply {
                  val intent =Intent(context,ChatActivity::class.java)
                  intent.putExtra("user",userrname.text)
                  intent.putExtra("resim",user.profileImage)
                  intent.putExtra("idd",user.userId)

                  startActivity(context,intent,null)
              }
          }

        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}