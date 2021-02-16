package com.vitaly.skywebtest.application

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SkyWebTestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin { androidContext(this@SkyWebTestApp) }
    }
}