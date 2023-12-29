package com.toquete.stopclenching.utils

import com.toquete.stopclenching.model.AlarmItem

abstract class AlarmScheduler {

    fun schedule(item: AlarmItem) {
        val initialTimeInMillis = getTimeInMillis(item.from)
        val finalTimeInMillis = getTimeInMillis(item.to)

        for (time in initialTimeInMillis until finalTimeInMillis step item.intervalMillis.toInt()) {
            setDailyRepeating(time)
        }
    }

    abstract fun cancel(item: AlarmItem)

    abstract fun setDailyRepeating(triggerTimeAtMillis: Int)

    abstract fun getTimeInMillis(time: String): Int
}