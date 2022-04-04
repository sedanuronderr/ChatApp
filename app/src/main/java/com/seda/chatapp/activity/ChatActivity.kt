package com.seda.chatapp.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.gson.Gson
import com.seda.chatapp.R
import com.seda.chatapp.adapter.ChatAdapter
import com.seda.chatapp.adapter.UserAdapter
import com.seda.chatapp.databinding.ActivityChatBinding
import com.seda.chatapp.model.Chat
import com.seda.chatapp.model.NotificationData
import com.seda.chatapp.model.PushNotification
import com.seda.chatapp.model.User
import com.seda.chatapp.retrofit

import com.seda.chatapp.utils.GlideLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ChatActivity : AppCompatActivity() {
    private lateinit var binding:ActivityChatBinding
    private lateinit var firebaseUser: FirebaseUser

    var chatList =ArrayList<Chat>()
    var topic = ""
    private lateinit var adapter: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user= intent.getStringExtra("user")
        val resim = intent.getStringExtra("resim")
        val receiveidd=intent.getStringExtra("idd")

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        if (resim != null) {
            GlideLoader(this).loadUserPicture(resim,binding.imgprofile)
        }

binding.usernamee.text= user

        binding.imgback.setOnClickListener {
            onBackPressed()
        }
        binding.btnmessage.setOnClickListener {
            var message:String= binding.mesaj.text.toString()

            if (message.isEmpty()){
                Toast.makeText(this,"message is empty",Toast.LENGTH_SHORT).show()
                binding.mesaj.setText("")
            }
            else{
              sendMessage(firebaseUser.uid,receiveidd!!,message)
                   binding.mesaj.setText("")

                topic = "/topics/$receiveidd"
                PushNotification(
                    NotificationData( user!!,message),
                    topic).also {
                    sendNotification(it)
                }
            }
        }

      getMessage(firebaseUser.uid,receiveidd!!)
        binding.recyclerChat.layoutManager =LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        adapter= ChatAdapter(this,chatList)
      binding.recyclerChat.adapter= adapter
        binding.recyclerChat.setHasFixedSize(true)


    }

 fun sendMessage(senderId:String,receiverId:String,message:String){
        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Chat")
        val hashMap:HashMap<String,String> = HashMap()
        hashMap.put("senderId",senderId)
        hashMap.put("receiverId", receiverId)
        hashMap.put("message",message)


        reference.child(java.lang.String.valueOf(System.currentTimeMillis())).setValue(hashMap)

    }
    fun getMessage(senderId: String, receiverId: String){
        val databaseReference:DatabaseReference = FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for(k: DataSnapshot in snapshot.children){
                    val chat = k.getValue(Chat::class.java)

                    if(chat!!.senderId.equals(senderId)&& chat.receiverId.equals(receiverId)
                        ||  chat.senderId.equals(receiverId) && chat.receiverId.equals(senderId)
                    ){
                        chat.userId = k.key.toString()

                        chatList.add(chat)

                    }




                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"error",Toast.LENGTH_SHORT).show()

            }

        })
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = retrofit.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d("TAG", "Response: ${Gson().toJson(response)}")
            } else {
                Log.e("TAG", response.errorBody()!!.string())
            }
        } catch(e: Exception) {
            Log.e("TAG", e.toString())
        }
    }
}