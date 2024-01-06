package com.toquete.stopclenching.infrastructure.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.toquete.stopclenching.Resources
import com.toquete.stopclenching.utils.Notification
import com.toquete.stopclenching.utils.NotificationHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AlarmReceiver : BroadcastReceiver(), KoinComponent {

    private val notificationHelper: NotificationHelper by inject()

    override fun onReceive(context: Context, intent: Intent) {
        val builder = Notification.Builder()
            .setSmallIcon(Resources.images.notification_important)
            .setTitle("Stop clenching")
            .setText("Time to relax your jaw")
            .setPriority(Notification.Priority.DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)

        notificationHelper.notify(builder.build())
    }

    companion object {
        const val SCHEME = "alarm"
    }
}