package com.example.moodyapplication.util

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.moodyapplication.R
import com.example.moodyapplication.model.MusicModel


const val CHANNEL_ID = "channel1"

class CreateNotification {


    @SuppressLint("UnspecifiedImmutableFlag")
    fun createNotification(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManagerCompat = NotificationManagerCompat.from(context)


           val  notification = NotificationCompat.Builder(context , CHANNEL_ID)
                .setSmallIcon(R.drawable.app_icon)
               .setContentTitle("welcome to our application")
               .setContentText("enjoy our music app with your mood")
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .build()

            notificationManagerCompat.notify(1 , notification)

        }
    }
}