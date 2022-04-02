package com.seda.chatapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.seda.chatapp.R
import com.seda.chatapp.activity.ChatActivity
import com.seda.chatapp.databinding.ActivityChatBinding.inflate
import com.seda.chatapp.databinding.ItemLeftBinding
import com.seda.chatapp.databinding.ItemRightBinding
import com.seda.chatapp.model.Chat


class ChatAdapter(private val context: Context,private val chatList: ArrayList<Chat>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

private val MESSAGE_TYPE_LEFT = 0
    private val MESSAGE_TYPE_RIGHT =1
    var firebaseUser:FirebaseUser?=null
    class ItemLeftViewHolder(private val Itemleft: ItemLeftBinding) :
        RecyclerView.ViewHolder(Itemleft.root) {
        fun bind(chat: Chat) {
            Itemleft.userrNameleft.text= chat.message

        }

    }

    class ItemRightViewHolder(private val ItemRigh: ItemRightBinding) :
      RecyclerView.ViewHolder(ItemRigh.root) {
        fun bind(chat:Chat) {
            ItemRigh.userrNameRight.text= chat.message

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MESSAGE_TYPE_RIGHT) {
            return ItemRightViewHolder(ItemRightBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        }else{
            return  ItemLeftViewHolder(ItemLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

        }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = chatList[position]

        if(getItemViewType(position) == MESSAGE_TYPE_RIGHT){
            (holder as ItemRightViewHolder ).bind(user)

        }else{
            (holder as ItemLeftViewHolder).bind(user)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }
    override fun getItemViewType(position: Int): Int {
       firebaseUser =FirebaseAuth.getInstance().currentUser
        if(chatList[position].senderId == firebaseUser!!.uid){
            return MESSAGE_TYPE_RIGHT
        }else{
            return MESSAGE_TYPE_LEFT
        }
    }
}