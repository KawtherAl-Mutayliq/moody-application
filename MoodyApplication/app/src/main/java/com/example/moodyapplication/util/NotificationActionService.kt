package com.example.moodyapplication.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationActionService : BroadcastReceiver() {
    override fun onReceive(context: Context? , intent: Intent?) {
        context?.sendBroadcast(Intent("Music_Music")
            .putExtra("action_name", intent?.action))
    }
}