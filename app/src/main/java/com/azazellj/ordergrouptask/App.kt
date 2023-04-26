package com.azazellj.ordergrouptask

import android.app.Application
import com.azazellj.ordergrouptask.di.KoinCoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                KoinCoreModule.koinCoreModule,
            )
        }
    }
}