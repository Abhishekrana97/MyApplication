package com.example.myapplication.data.firebase


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat
import com.example.myapplication.presentation.ui.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class FirebaseNotificationService : FirebaseMessagingService() {

    private val CHANNEL_ID = "head_up_notification_channel"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle the notification message data
        Timber.e(">>>>>>>>>>>>>>> ${remoteMessage.notification?.body}")

        remoteMessage.notification?.let {
            // Call the method to show a head-up notification
            if (isAppInForeground())
                Timber.e(">>>>>>>>>>>>>>> ${it.title.toString()}  >>>>  ${it.body.toString()}")
            else
                handleNotification(it.title.toString(), it.body.toString())
        }

    }

    private fun handleNotification(title: String, body: String) {
        // Create the notification channel for Android 8.0 (Oreo) and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "High Priority Notifications",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for head-up notifications"
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
            }

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Create an Intent to launch your app when notification is clicked
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Build the notification with FullScreenIntent
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setFullScreenIntent(pendingIntent, true)  // Forces a head-up notification
            .build()

        // Display the notification
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }


    override fun onNewToken(token: String) {
        // Send the token to your server if necessary
        // Save or update the token for later use
        Timber.e("Token>>>>>>>> $token")
    }

    private fun isAppInForeground(): Boolean {
        // A simple check for whether the app is in the foreground
        val activityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
        val runningAppProcesses = activityManager.runningAppProcesses
        for (process in runningAppProcesses) {
            if (process.processName == packageName && process.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true
            }
        }
        return false
    }
}

