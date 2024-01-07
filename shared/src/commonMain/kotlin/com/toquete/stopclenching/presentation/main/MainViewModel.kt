package com.toquete.stopclenching.presentation.main

import com.toquete.stopclenching.model.AlarmItem
import com.toquete.stopclenching.utils.AlarmScheduler
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val scheduler: AlarmScheduler
) : ViewModel() {

    private val _event = Channel<MainEvent>()
    val event = _event.receiveAsFlow()

    fun onScheduleAlarmClick(
        alarmItem: AlarmItem,
        isNotificationPermissionGranted: Boolean,
        canScheduleAlarms: Boolean
    ) {
        viewModelScope.launch {
            when {
                isNotificationPermissionGranted && canScheduleAlarms -> {
                    scheduler.schedule(alarmItem)
                }
                !isNotificationPermissionGranted -> {
                    _event.send(MainEvent.LaunchNotificationPermissionRequest)
                }
                else -> {
                    _event.send(MainEvent.LaunchAlarmPermissionRequest)
                }

            }
        }
    }

    fun onCancelAlarmClick(alarmItem: AlarmItem) {
        scheduler.cancel(alarmItem)
    }

    fun onNotificationPermissionResult(canScheduleExactAlarms: Boolean) {
        viewModelScope.launch {
            if (!canScheduleExactAlarms) {
                _event.send(MainEvent.LaunchAlarmPermissionRequest)
            }
        }

    }
}