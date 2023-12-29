package com.toquete.stopclenching.di

import com.toquete.stopclenching.presentation.main.MainViewModel
import com.toquete.stopclenching.utils.AlarmScheduler
import com.toquete.stopclenching.utils.IOSAlarmScheduler
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val iosModule = module {
    singleOf(::IOSAlarmScheduler) bind AlarmScheduler::class
    singleOf(::MainViewModel)
}