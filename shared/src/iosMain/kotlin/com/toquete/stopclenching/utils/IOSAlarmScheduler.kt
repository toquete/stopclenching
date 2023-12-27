package com.toquete.stopclenching.utils

import com.toquete.stopclenching.model.AlarmItem
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents
import platform.Foundation.NSUUID
import platform.UserNotifications.UNCalendarNotificationTrigger
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNUserNotificationCenter

class IOSAlarmScheduler : AlarmScheduler {

    override fun schedule(item: AlarmItem) {
        val initialTime = item.from.toLocalTime().toMillisecondOfDay()
        val finalTime = item.to.toLocalTime().toMillisecondOfDay()

        UNUserNotificationCenter.currentNotificationCenter().removeAllPendingNotificationRequests()

        val content = UNMutableNotificationContent().apply {
            setTitle("Stop clenching")
            setBody("Time to relax your jaw")
        }

        for (time in initialTime until finalTime step item.intervalMillis.toInt()) {
            val date = NSDateComponents().apply {
                calendar = NSCalendar.currentCalendar
                hour = LocalTime.fromMillisecondOfDay(time).hour.toLong()
                minute = LocalTime.fromMillisecondOfDay(time).minute.toLong()
            }

            val trigger = UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(date, repeats = true)

            val request = UNNotificationRequest.requestWithIdentifier(
                NSUUID.UUID().UUIDString,
                content = content,
                trigger = trigger
            )

            UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(
                request,
                withCompletionHandler = null
            )
        }
    }

    override fun cancel(item: AlarmItem) {
        UNUserNotificationCenter.currentNotificationCenter().removeAllDeliveredNotifications()
        UNUserNotificationCenter.currentNotificationCenter().removeAllPendingNotificationRequests()
    }
}