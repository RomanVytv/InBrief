package com.romanvytv.inbrief

import android.app.Application
import com.romanvytv.inbrief.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InBriefApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@InBriefApp)
            modules(appModule)
        }
    }
} 