package com.seda.chatapp.FirebaseStorage

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.seda.chatapp.Constants.Constans
import com.seda.chatapp.ProfileActivity

class FirebaseClass {
    companion object {
        fun uploadImage(activity: Activity, imageFileUri: Uri?) {
            val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                Constans.USER_PROFILE + System.currentTimeMillis() + "." + Constans.getMimeType(
                    activity,
                    imageFileUri
                )
            )

            sRef.putFile(imageFileUri!!).addOnSuccessListener { taskSnapshot ->
                Log.e("Firebase image", taskSnapshot.metadata!!.reference!!.downloadUrl.toString())

                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                    when (activity) {
                        is ProfileActivity -> {
                            activity.imageUploadSuccess(uri.toString())
                        }
                    }
                }
            }
        }



}}