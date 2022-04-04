package com.seda.chatapp.Interfacee

import com.seda.chatapp.Constants.FcmConstans.Companion.CONTENT_TYPE
import com.seda.chatapp.Constants.FcmConstans.Companion.SERVER_KEY
import com.seda.chatapp.model.PushNotification
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Notification {

    @Headers("Authorization: key=$SERVER_KEY","Content-type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}