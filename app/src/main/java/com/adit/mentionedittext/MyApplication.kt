package com.adit.mentionedittext

import android.app.Application
import timber.log.Timber

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}