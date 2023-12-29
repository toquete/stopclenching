package com.toquete.stopclenching.di

import com.toquete.stopclenching.presentation.main.MainViewModel
import com.toquete.stopclenching.utils.AlarmScheduler
import com.toquete.stopclenching.utils.AndroidAlarmScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    single<AlarmScheduler> {
        AndroidAlarmScheduler(androidContext(), get())
    }
    viewModel { MainViewModel(get()) }
}