package com.toquete.stopclenching.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.net.toUri
import com.toquete.stopclenching.infrastructure.receiver.AlarmReceiver
import com.toquete.stopclenching.model.AlarmItem
import kotlinx.datetime.toLocalTime
import java.util.Calendar

class AndroidAlarmScheduler(
    private val context: Context,
    private val alarmManager: AlarmManager?
) : AlarmScheduler() {

    override fun cancel(item: AlarmItem) {
        val initialTimeInMillis = getTimeInMillis(item.from)
        val finalTimeInMillis = getTimeInMillis(item.to)

        for (time in initialTimeInMillis until finalTimeInMillis step item.intervalMillis.toLong()) {
            alarmManager?.cancel(getPendingIntent(time))
        }
    }

    override fun setDailyRepeating(triggerTimeAtMillis: Long) {
        alarmManager?.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !canScheduleExactAlarms()) {
                return
            }

            val fixedTriggerTimeAtMillis = if (System.currentTimeMillis() > triggerTimeAtMillis) {
                triggerTimeAtMillis + AlarmManager.INTERVAL_DAY
            } else {
                triggerTimeAtMillis
            }

            val pendingIntent = getPendingIntent(fixedTriggerTimeAtMillis)

            setRepeating(
                AlarmManager.RTC_WAKEUP,
                fixedTriggerTimeAtMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }

    override fun getTimeInMillis(time: String): Long {
        val localTime = time.toLocalTime()
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, localTime.hour)
            set(Calendar.MINUTE, localTime.minute)
            set(Calendar.SECOND, localTime.second)
        }

        return calendar.timeInMillis
    }

    override fun canScheduleExactAlarms(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager?.canScheduleExactAlarms() ?: false
        } else {
            true
        }
    }

    private fun getPendingIntent(timeAtMillis: Long): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            data = "${AlarmReceiver.SCHEME}://$timeAtMillis".toUri()
        }
        return PendingIntent.getBroadcast(
            context,
            REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        const val REQUEST_CODE = 1000
    }
}