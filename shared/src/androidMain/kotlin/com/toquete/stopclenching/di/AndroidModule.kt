package com.toquete.stopclenching.di

import android.app.AlarmManager
import com.toquete.stopclenching.presentation.main.MainViewModel
import com.toquete.stopclenching.utils.AlarmScheduler
import com.toquete.stopclenching.utils.AndroidAlarmScheduler
import com.toquete.stopclenching.utils.AndroidNotificationHelper
import com.toquete.stopclenching.utils.NotificationHelper
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val androidModule = module {
    single<AlarmScheduler> {
        AndroidAlarmScheduler(
            context = androidContext(),
            alarmManager = androidContext().getSystemService(AlarmManager::class.java)
        )
    }
    single<NotificationHelper> { AndroidNotificationHelper(androidContext()) }
    viewModelOf(::MainViewModel)
}