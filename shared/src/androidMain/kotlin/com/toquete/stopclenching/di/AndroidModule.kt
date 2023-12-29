package com.toquete.stopclenching.di

import android.app.AlarmManager
import com.toquete.stopclenching.presentation.main.MainViewModel
import com.toquete.stopclenching.utils.AlarmScheduler
import com.toquete.stopclenching.utils.AndroidAlarmScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    single<AlarmScheduler> {
        AndroidAlarmScheduler(
            context = androidContext(),
            alarmAction = get(),
            alarmManager = androidContext().getSystemService(AlarmManager::class.java)
        )
    }
    viewModel { MainViewModel(get()) }
}