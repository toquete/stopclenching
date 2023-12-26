package com.toquete.stopclenching.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import com.toquete.stopclenching.model.AlarmItem
import kotlinx.datetime.toLocalTime
import java.util.Calendar

class AndroidAlarmScheduler(
    context: Context,
    private val pendingIntent: (AlarmItem) -> PendingIntent
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: AlarmItem) {
        val initialTimeInMillis = getTimeInMillis(item.from)
        val finalTimeInMillis = getTimeInMillis(item.to)

        for (time in initialTimeInMillis until finalTimeInMillis step item.intervalMillis) {
            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                time,
                AlarmManager.INTERVAL_DAY,
                pendingIntent(item)
            )
        }
    }

    override fun cancel(item: AlarmItem) {
        alarmManager?.cancel(pendingIntent(item))
    }

    private fun getTimeInMillis(time: String): Long {
        val localTime = time.toLocalTime()
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, localTime.hour)
            set(Calendar.MINUTE, localTime.minute)
        }

        return calendar.timeInMillis
    }
}