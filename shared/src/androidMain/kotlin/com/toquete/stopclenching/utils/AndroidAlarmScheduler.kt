package com.toquete.stopclenching.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.toquete.stopclenching.model.AlarmItem
import kotlinx.datetime.toLocalTime
import java.util.Calendar

class AndroidAlarmScheduler(
    private val context: Context,
    private val alarmAction: AlarmAction
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
                getPendingIntent(time)
            )
        }
    }

    override fun cancel(item: AlarmItem) {
        val initialTimeInMillis = getTimeInMillis(item.from)
        val finalTimeInMillis = getTimeInMillis(item.to)

        for (time in initialTimeInMillis until finalTimeInMillis step item.intervalMillis) {
            alarmManager?.cancel(getPendingIntent(time))
        }
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

    private fun getPendingIntent(time: Long): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            alarmAction.getIntent(time),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    interface AlarmAction {
        fun getIntent(time: Long): Intent
    }

    companion object {
        const val REQUEST_CODE = 1000
    }
}