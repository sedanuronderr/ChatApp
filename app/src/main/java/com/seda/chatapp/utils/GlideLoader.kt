package com.seda.chatapp.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.seda.chatapp.R
import java.io.IOException

class GlideLoader(val context: Context) {

    fun loadUserPicture(imageURI: Any, imageView: ImageView){
        try {
            Glide
                .with(context)
                .load(Uri.parse(imageURI.toString()))
                .centerCrop()
                .placeholder(R.drawable.acount)
                .into(imageView)
        }catch (e: IOException){
            e.printStackTrace()
        }
    }


}