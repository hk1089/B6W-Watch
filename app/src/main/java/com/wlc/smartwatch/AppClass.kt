package com.wlc.smartwatch

import android.app.Application
import com.wlc.smartwatch.modules.AppModule
import com.wlc.smartwatch.modules.Scopes
import timber.log.Timber
import toothpick.Toothpick

class AppClass: Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
        initLogger()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initDI() {
        Toothpick.openScope(Scopes.APP).installModules(
            AppModule(
                applicationContext
            )
        )

    }
}