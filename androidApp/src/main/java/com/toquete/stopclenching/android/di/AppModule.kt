package com.toquete.stopclenching.android.di

import com.toquete.stopclenching.android.NotificationAlarmAction
import com.toquete.stopclenching.utils.AndroidAlarmScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<AndroidAlarmScheduler.AlarmAction> { NotificationAlarmAction(androidContext()) }
}