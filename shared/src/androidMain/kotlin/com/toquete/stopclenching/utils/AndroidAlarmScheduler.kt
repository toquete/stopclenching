package com.toquete.stopclenching.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.toquete.stopclenching.infrastructure.receiver.AlarmReceiver
import com.toquete.stopclenching.model.AlarmItem
import kotlinx.datetime.LocalTime
import java.util.Calendar

class AndroidAlarmScheduler(
    private val context: Context,
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

    override fun getTimeInMillis(time: LocalTime): Int {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
        }

        return calendar.timeInMillis.toInt()
    }

    private fun getPendingIntent(timeAtMillis: Int): PendingIntent {
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