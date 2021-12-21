package com.example.moodyapplication.util

import android.app.Notification
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media.app.NotificationCompat
import com.example.moodyapplication.model.MusicModel

private const val CHANNEL_ID = "channel1"
private const val ACTION_PREVIOUS = "actionprevious"
private const val MUSIC_PLAY = "actionplay"
private const val MUSIC_NEXT = "actionnext"

class CreateNotification {

    lateinit var notification: Notification

    fun createNotification(
        context: Context ,
        musicModel: MusicModel ,
        playButton: Int ,
        position: Int ,
        musicSize: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            val mediaSessionOnCompat = MediaSessionCompat(context, "tag")

            val bitmap = BitmapFactory.decodeResource(context.resources, musicModel.photo.toInt())


        }
    }
}