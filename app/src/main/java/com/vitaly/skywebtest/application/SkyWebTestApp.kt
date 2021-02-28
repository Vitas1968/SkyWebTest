package com.vitaly.skywebtest.application

import android.app.Application
import com.vitaly.skywebtest.di.authModule


import com.vitaly.skywebtest.di.photosModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class SkyWebTestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SkyWebTestApp)
            modules(photosModule,authModule)
        }
    }
}