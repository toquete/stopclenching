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
    private val alarmAction: AlarmAction,
    private val alarmManager: AlarmManager?
) : AlarmScheduler() {

    override fun cancel(item: AlarmItem) {
        val initialTimeInMillis = getTimeInMillis(item.from)
        val finalTimeInMillis = getTimeInMillis(item.to)

        for (time in initialTimeInMillis until finalTimeInMillis step item.intervalMillis) {
            alarmManager?.cancel(getPendingIntent(time))
        }
    }

    override fun setDailyRepeating(triggerTimeAtMillis: Int) {
        alarmManager?.run {
            val pendingIntent = getPendingIntent(triggerTimeAtMillis)

            cancel(pendingIntent)
            setRepeating(
                AlarmManager.RTC_WAKEUP,
                triggerTimeAtMillis.toLong(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }

    override fun getTimeInMillis(time: String): Int {
        val localTime = time.toLocalTime()
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, localTime.hour)
            set(Calendar.MINUTE, localTime.minute)
        }

        return calendar.timeInMillis.toInt()
    }

    private fun getPendingIntent(timeAtMillis: Int): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            alarmAction.getIntent(timeAtMillis),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    interface AlarmAction {
        fun getIntent(timeAtMillis: Int): Intent
    }

    companion object {
        const val REQUEST_CODE = 1000
    }
}