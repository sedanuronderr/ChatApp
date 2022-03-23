package com.seda.chatapp.Constants

import android.app.Activity
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment

object Constans {
    const val USER_PROFILE:String="User_profile_mage"


    fun getMimeType(activity: Activity, path: Uri?): String? {

        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(path!!))


    }
}