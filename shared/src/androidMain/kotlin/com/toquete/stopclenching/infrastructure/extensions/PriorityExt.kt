package com.toquete.stopclenching.infrastructure.extensions

import androidx.core.app.NotificationCompat
import com.toquete.stopclenching.utils.Notification

fun Notification.Priority.toAndroidPriority(): Int {
    return when (this) {
        Notification.Priority.MAX -> NotificationCompat.PRIORITY_MAX
        Notification.Priority.HIGH -> NotificationCompat.PRIORITY_HIGH
        Notification.Priority.DEFAULT -> NotificationCompat.PRIORITY_DEFAULT
        Notification.Priority.LOW -> NotificationCompat.PRIORITY_LOW
    }
}