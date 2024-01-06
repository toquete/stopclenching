package com.toquete.stopclenching.utils

interface NotificationHelper {

    fun notify(notification: Notification, triggerTimeAtMillis: Int = 0)
}