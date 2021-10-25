package com.wlc.smartwatch.modules

import android.content.Context
import com.wlc.smartwatch.storage.PrefStorage
import toothpick.config.Module

class AppModule(context: Context): Module() {

    init {
        bind(Context::class.java).toInstance(context)
        bind(PrefStorage::class.java).toInstance(PrefStorage(context))

    }
}