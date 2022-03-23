package com.seda.chatapp.model

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable
@IgnoreExtraProperties
data class User (var userId:String?=null,var username:String?=null,var profileImage:String?=null):Serializable