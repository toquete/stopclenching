package com.toquete.stopclenching.android

import android.app.Application
import com.toquete.stopclenching.android.di.appModule
import com.toquete.stopclenching.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class StopClenchingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@StopClenchingApplication)
            modules(appModule + androidModule)
        }
    }
}