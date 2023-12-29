package com.toquete.stopclenching.di

import com.toquete.stopclenching.presentation.main.MainViewModel
import com.toquete.stopclenching.utils.AlarmScheduler
import com.toquete.stopclenching.utils.IOSAlarmScheduler
import org.koin.dsl.module

val iosModule = module {
    single<AlarmScheduler> { IOSAlarmScheduler() }
    single { MainViewModel(get())}
}