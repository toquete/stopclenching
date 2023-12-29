package com.toquete.stopclenching.presentation.main

import com.toquete.stopclenching.model.AlarmItem
import com.toquete.stopclenching.utils.AlarmScheduler
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.datetime.toLocalTime

class MainViewModel(
    private val scheduler: AlarmScheduler
) : ViewModel() {

    fun onScheduleAlarmClick(
        from: String,
        to: String,
        intervalMillis: Int
    ) {
        scheduler.schedule(
            item = AlarmItem(
                from = from.toLocalTime(),
                to = to.toLocalTime(),
                intervalMillis = intervalMillis
            )
        )
    }

    fun onCancelAlarmClick(
        from: String,
        to: String,
        intervalMillis: Int
    ) {
        scheduler.cancel(
            item = AlarmItem(
                from = from.toLocalTime(),
                to = to.toLocalTime(),
                intervalMillis = intervalMillis
            )
        )
    }
}