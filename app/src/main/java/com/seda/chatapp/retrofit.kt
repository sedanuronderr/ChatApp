package com.seda.chatapp

import com.google.firebase.messaging.Constants
import com.seda.chatapp.Constants.Constans
import com.seda.chatapp.Constants.FcmConstans
import com.seda.chatapp.Interfacee.Notification
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class retrofit {
    companion object{
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(FcmConstans.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api by lazy {
            retrofit.create(Notification::class.java)
        }
    }
}