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
const val ACTION_PREVIOUS = "actionprevious"
const val ACTION_PLAY = "actionplay"
const val ACTION_NEXT = "actionnext"
private const val TAG = "CreateNotification"
class CreateNotification {

    lateinit var notification: Notification


    @SuppressLint("UnspecifiedImmutableFlag")
    fun createNotification(
        context: Context ,
        musicModel: MutableList<MusicModel> ,
        position: Int ,
        musicSize: Int
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManagerCompat = NotificationManagerCompat.from(context)
            val mediaSessionOnCompat = MediaSessionCompat(context , "tag")


//            val pendingIntentPrevious: PendingIntent?
//            val previous: Int
//            if (position == 0) {
//                pendingIntentPrevious = null
//                previous = 0
//            } else {
//                val intentPrevious = Intent(context , NotificationActionService::class.java)
//                    .setAction(ACTION_PREVIOUS)
//                pendingIntentPrevious = PendingIntent.getBroadcast(
//                    context ,
//                    0 ,
//                    intentPrevious ,
//                    PendingIntent.FLAG_UPDATE_CURRENT
//                )
//                previous = R.drawable.ic_baseline_fast_rewind_black
//            }
//
//
//
//            val intentPlay = Intent(context , NotificationActionService::class.java)
//                .setAction(ACTION_PLAY)
//           val  pendingIntentPlay= PendingIntent.getBroadcast(
//                context ,
//                0 ,
//                intentPlay ,
//                PendingIntent.FLAG_UPDATE_CURRENT
//            )



////            val pendingIntentNext: PendingIntent?
//            val next: Int
////            if (position == musicSize) {
////                pendingIntentNext = null
////                next = 0
////            } else {
////                val intentNext = Intent(context , NotificationActionService::class.java)
////                    .setAction(ACTION_NEXT)
////                pendingIntentNext = PendingIntent.getBroadcast(
////                    context ,
////                    0 ,
////                    intentNext ,
////                    PendingIntent.FLAG_UPDATE_CURRENT
////                )
//                next = R.drawable.ic_baseline_fast_forward_black
////            }



           val  notification = NotificationCompat.Builder(context , CHANNEL_ID)
                .setSmallIcon(R.drawable.applogo)
                .setContentTitle(musicModel[position].name)
                .setContentText(musicModel[position].description)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.ic_baseline_fast_rewind_black, "previous", null)
               .addAction(R.drawable.ic_baseline_fast_forward_black, "play", null)
                .addAction(R.drawable.ic_baseline_play_arrow_black, "next", null)
               .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                   .setShowActionsInCompactView(1,2,3))
                .setShowWhen(false)
                .build()

            notificationManagerCompat.notify(1 , notification)

        }
    }
}