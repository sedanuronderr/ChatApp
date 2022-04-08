package com.seda.chatapp.model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable
@IgnoreExtraProperties

data class Chat (var userId:String?=null,var senderId:String?=null, var receiverId:String?=null, var message:String?=null,var image:String?=null)



