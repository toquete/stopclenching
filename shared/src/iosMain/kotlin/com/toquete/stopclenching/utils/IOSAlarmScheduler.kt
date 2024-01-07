package com.toquete.stopclenching.utils

import com.toquete.stopclenching.model.AlarmItem
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateComponents
import platform.Foundation.NSUUID
import platform.UserNotifications.UNAuthorizationStatusAuthorized
import platform.UserNotifications.UNCalendarNotificationTrigger
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNUserNotificationCenter

class IOSAlarmScheduler : AlarmScheduler() {

    override fun cancel(item: AlarmItem) {
        UNUserNotificationCenter.currentNotificationCenter().removeAllDeliveredNotifications()
        UNUserNotificationCenter.currentNotificationCenter().removeAllPendingNotificationRequests()
    }

    override fun setDailyRepeating(triggerTimeAtMillis: Long) {
        UNUserNotificationCenter.currentNotificationCenter().removeAllPendingNotificationRequests()

        val content = UNMutableNotificationContent().apply {
            setTitle("Stop clenching")
            setBody("Time to relax your jaw")
        }

        val date = NSDateComponents().apply {
            calendar = NSCalendar.currentCalendar
            hour = LocalTime.fromMillisecondOfDay(triggerTimeAtMillis.toInt()).hour.toLong()
            minute = LocalTime.fromMillisecondOfDay(triggerTimeAtMillis.toInt()).minute.toLong()
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

    override fun getTimeInMillis(time: String): Long {
        return time.toLocalTime().toMillisecondOfDay().toLong()
    }

    override fun canScheduleExactAlarms(): Boolean {
        var canSchedule = false

        UNUserNotificationCenter.currentNotificationCenter().getNotificationSettingsWithCompletionHandler { settings ->
            canSchedule =  settings?.authorizationStatus == UNAuthorizationStatusAuthorized
        }

        return canSchedule
    }
}