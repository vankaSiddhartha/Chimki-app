package com.vanka.finalchimki

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import com.vanka.finalchimki.Heart
import com.vanka.finalchimki.R
var q =0
var channelId = "Id"
var Id = 0
var channelName = "Name"
class MyFirebaseInstanceIDService:FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
    override fun onMessageReceived(message: RemoteMessage) {

        super.onMessageReceived(message)

        sendNoti(message.notification!!.title,message.notification!!.body)

    }

    private fun sendNoti(title: String?, body: String?) {
        var sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var notification = NotificationCompat.Builder(this,"jai Vinayaka")
            .setSmallIcon(R.drawable.notification)
            .setContentTitle(title)
            .setContentText(body)
            .setSound(sound)
            .setAutoCancel(true)
            .build()
          val notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notiManager.notify(55,notification)

    }


}


