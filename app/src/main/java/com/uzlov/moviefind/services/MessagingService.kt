package com.uzlov.moviefind.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.uzlov.moviefind.R
import com.uzlov.moviefind.activities.HostActivity

class MessagingService  : FirebaseMessagingService() {
    private val NOTIFICATION_ID : Int = 1
    private val CHANNEL_ID  by lazy {resources.getString(R.string.default_notification_channel_id)}

    companion object{
        const val VALUE_KEY: String = "sample_key"
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(message: RemoteMessage) {

       val manager =  createNotificationChannel()
        val notificationIntent = Intent(this, HostActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.funny_string))
            .setSmallIcon(R.drawable.ic_baseline_favorite_border_24)
            .setContentText(message.notification?.body ?: "Empty body")
            .setContentIntent(pendingIntent)

        manager?.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun createNotificationChannel() : NotificationManager? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.string_from_moviefind),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
            return manager
        }
        return null
    }
}