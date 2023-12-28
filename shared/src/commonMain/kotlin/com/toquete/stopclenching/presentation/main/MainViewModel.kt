package com.toquete.stopclenching.presentation.main

import com.toquete.stopclenching.model.AlarmItem
import com.toquete.stopclenching.utils.AlarmScheduler
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class MainViewModel(
    private val scheduler: AlarmScheduler
) : ViewModel() {

    fun onScheduleAlarmClick(alarmItem: AlarmItem) {
        scheduler.schedule(alarmItem)
    }

    fun onCancelAlarmClick(alarmItem: AlarmItem) {
        scheduler.cancel(alarmItem)
    }
}