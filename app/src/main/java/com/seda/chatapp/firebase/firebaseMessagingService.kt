package com.seda.chatapp.firebase

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.seda.chatapp.R
import com.seda.chatapp.activity.ChatActivity
import com.seda.chatapp.activity.UserActivity
import kotlin.random.Random

const val channelId = "notification_channel"
const val channelName ="com.seda.chatapp.firebase"

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class firebaseMessagingService:FirebaseMessagingService() {
    companion object{
        var sharedPref: SharedPreferences? = null

        var token:String?
            get(){
                return sharedPref?.getString("token","")
            }
            set(value){
                sharedPref?.edit()?.putString("token",value)?.apply()
            }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.e("message","message recive")
        val intent = Intent(this,UserActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val notificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random.nextInt()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationCompact =NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationCompact)
        }
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)
        val builder:NotificationCompat.Builder =NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["message"])
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        notificationManager.notify(notificationId,builder.build())

    }

    override fun onNewToken(token1: String) {
        super.onNewToken(token1)

        token = token1
    }

  /*  private fun getRemoveView(title: String, message: String): RemoteViews? {
val remoteView =RemoteViews("com.seda.chatapp.firebase",R.layout.notification)

   remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.description,message)
remoteView.setImageViewResource(R.id.app_logo,R.drawable.notification)
        return remoteView
    }

    fun generateNotification(title:String,message:String){
        val intent = Intent(this,ChatActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)
var builder:NotificationCompat.Builder =NotificationCompat.Builder(applicationContext, channelId)
    .setSmallIcon(R.drawable.notification)
    .setAutoCancel(true)
    .setVibrate(longArrayOf(1000,1000,1000,1000))
    .setOnlyAlertOnce(true)
    .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoveView(title,message))

        val notificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationCompact =NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
               notificationManager.createNotificationChannel(notificationCompact)
        }
notificationManager.notify(0,builder.build())
    }


*/
}