package com.toquete.stopclenching.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.toquete.stopclenching.infrastructure.extensions.toAndroidPriority

private const val CHANNEL_ID = "alarm_channel_id"
private const val NOTIFICATION_ID = 1

class AndroidNotificationHelper(
    private val context: Context
) : NotificationHelper {

    override fun notify(notification: Notification, triggerTimeAtMillis: Int) {
        createNotificationChannel(context)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(notification.smallIcon.drawableResId)
            .setContentTitle(notification.title)
            .setContentText(notification.text)
            .setPriority(notification.priority.toAndroidPriority())
            .setCategory(notification.category)

        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context)
                .notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Reminders"
            val descriptionText = "Reminders to stop clenching"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableLights(true)
                enableVibration(true)
            }
            context.getSystemService(NotificationManager::class.java)?.run {
                createNotificationChannel(channel)
            }
        }
    }

}