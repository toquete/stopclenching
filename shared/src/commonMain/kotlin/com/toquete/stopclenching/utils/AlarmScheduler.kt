package com.toquete.stopclenching.utils

import com.toquete.stopclenching.model.AlarmItem

interface AlarmScheduler {

    fun schedule(item: AlarmItem)
    fun cancel(item: AlarmItem)
}