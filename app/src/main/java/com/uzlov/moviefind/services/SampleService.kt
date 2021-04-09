package com.uzlov.moviefind.services

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.uzlov.moviefind.HostActivity
import com.uzlov.moviefind.R

class SampleService  : Service() {
    private val CHANNEL_ID: String = "ForegroundServiceChannel"

    companion object{
        val VALUE_KEY: String = "sample_key"
        val ACTION = "ACTION_SAMPLE_SERVICE"
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notificationIntent = Intent(this, HostActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,
            0, notificationIntent, 0)
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.funny_string))
            .setSmallIcon(R.drawable.ic_baseline_favorite_border_24)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)

        Thread {
            for (i in 0..5){
                Thread.sleep(1000)
                val data = Intent().apply {
                    putExtra(VALUE_KEY, (i % 2 == 0).toString())
                    action = ACTION
                }
                sendBroadcast(data)
                if (i == 4) stopSelf()
            }
        }.start()

        return START_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.string_from_moviefind),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}