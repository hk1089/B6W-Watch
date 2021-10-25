package com.wlc.smartwatch.storage

import android.content.Context
import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.wlc.smartwatch.R
import javax.inject.Inject

class PrefStorage @Inject constructor(context: Context) {

    private val prefs = RxSharedPreferences.create(context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE))
}