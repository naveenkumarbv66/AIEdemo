package com.naveen.aiedemo.view.notifications

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.naveen.aiedemo.view.DashboardActivity

@Suppress("DEPRECATION")
class NotificationPublisher : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == ACTION_NAME) {
            createNotificationChannel(context, intent)
        }
    }

    private fun getNotification(context: Context?, intent: Intent?): Notification.Builder {
        return Notification.Builder(context).apply {
            setSmallIcon(R.drawable.ic_dialog_alert)
            setContentTitle(intent?.getStringExtra(TASK_NAME))
            setContentText(intent?.getStringExtra(TASK_DESCRIPTION))
            setPriority(Notification.PRIORITY_DEFAULT)
            setAutoCancel(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) setChannelId(NOTIFICATION_CHANNEL_ID)
            setDefaults(Notification.DEFAULT_SOUND)
            setContentIntent(PendingIntent.getActivity(context, NOTIFICATION_ID, Intent(context, DashboardActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT))
        }
    }

    private fun createNotificationChannel(context: Context?, intent: Intent?) {
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = getNotification(context, intent)
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance).apply {
                description = NOTIFICATION_CHANNEL_DESCRIPTION
            }
            // Register the channel with the system
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        const val ACTION_NAME = "MyBroadcastReceiverAction"
        const val NOTIFICATION_CHANNEL_ID = "App_AIE_notification"
        const val NOTIFICATION_CHANNEL_NAME = "AIE"
        const val NOTIFICATION_CHANNEL_DESCRIPTION = "AIE_Dec"
        const val NOTIFICATION_ID = 66

        const val TASK_NAME = "taskName"
        const val TASK_DESCRIPTION = "taskDescription"
    }

}