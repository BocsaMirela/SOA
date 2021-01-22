package com.example.soa.service

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.soa.dependencies.RepositoryModule
import com.example.soa.util.Constants.KEY_TOKEN
import com.example.soa.util.showNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {

    private var bundle: Bundle? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        bundle = Bundle()
        var title: String? = ""
        if (remoteMessage.notification?.title != null) {
            title = remoteMessage.notification!!.title
        }
        var message: String? = ""
        if (remoteMessage.notification?.body != null) {
            message = remoteMessage.notification!!.body
        }
        Log.e(javaClass.name, "onMessageReceived  title $title")
        Log.e(javaClass.name, "onMessageReceived  message $message")
        applicationContext.showNotification(title, message, priority = NotificationCompat.PRIORITY_HIGH)
    }

    override fun onNewToken(token: String) {
        Log.e(javaClass.name, "Messaging Service token $token")
        val preferences = applicationContext.getSharedPreferences(RepositoryModule.PREFERENCES, Context.MODE_PRIVATE)
        with(preferences.edit()) {
            putBoolean(KEY_TOKEN, false)
            commit()
        }
    }
}